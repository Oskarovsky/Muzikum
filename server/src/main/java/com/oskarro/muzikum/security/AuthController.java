package com.oskarro.muzikum.security;

import com.oskarro.muzikum.exception.AppException;
import com.oskarro.muzikum.exception.InvalidOldPasswordException;
import com.oskarro.muzikum.security.jwt.JwtTokenProvider;
import com.oskarro.muzikum.security.payload.JwtAuthenticationResponse;
import com.oskarro.muzikum.security.payload.ApiResponse;
import com.oskarro.muzikum.security.payload.LoginRequest;
import com.oskarro.muzikum.security.payload.RegisterRequest;
import com.oskarro.muzikum.user.*;

import java.util.*;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.oskarro.muzikum.user.email.EmailService;
import com.oskarro.muzikum.user.role.Role;
import com.oskarro.muzikum.user.role.RoleName;
import com.oskarro.muzikum.user.role.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import static java.time.Instant.now;

@Slf4j
@RestController
@RequestMapping(value = "/api/auth")
@CrossOrigin
public class AuthController {

    final AuthenticationManager authenticationManager;
    final UserRepository userRepository;
    final RoleRepository roleRepository;
    final UserStatisticsRepository userStatisticsRepository;
    final PasswordEncoder encoder;
    final JwtTokenProvider jwtTokenProvider;
    final UserDetailsServiceImpl userDetailsService;

    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final EmailService emailService;

    @Value("${spring.profiles.active}")
    private String activeProfile;

    public AuthController(AuthenticationManager authenticationManager,
                          UserRepository userRepository,
                          RoleRepository roleRepository,
                          UserStatisticsRepository userStatisticsRepository,
                          PasswordEncoder encoder,
                          JwtTokenProvider jwtTokenProvider,
                          ConfirmationTokenRepository confirmationTokenRepository,
                          EmailService emailService,
                          UserDetailsServiceImpl userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userStatisticsRepository = userStatisticsRepository;
        this.encoder = encoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.confirmationTokenRepository = confirmationTokenRepository;
        this.emailService = emailService;
        this.userDetailsService = userDetailsService;
    }

    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtTokenProvider.generateJwtToken(authentication);
        UserPrincipal userDetails = (UserPrincipal) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt,
                                        userDetails.getId(),
                                        userDetails.getEmail(),
                                        userDetails.getUsername(),
                                        roles));
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new ApiResponse(false,"Fail -> Username is already taken!"));
        } else if (userRepository.existsByEmail(registerRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new ApiResponse(false,"Fail -> Email is already in use!"));
        }

        // Creating new user's account
        User user = User.builder()
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .password(encoder.encode(registerRequest.getPassword()))
                .activated(false)
                .build();

        Set<String> stringRoles = registerRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (stringRoles == null) {
            Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
            roles.add(userRole);
        } else {
            stringRoles.forEach(role -> {
                switch (role) {
                    case "admin" -> {
                        Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
                        roles.add(adminRole);
                    }
                    case "mod" -> {
                        Role modRole = roleRepository.findByName(RoleName.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
                        roles.add(modRole);
                    }
                    default -> {
                        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
                        roles.add(userRole);
                    }
                }
            });
        }
        user.setProvider(AuthProvider.local);
        user.setRoles(roles);
        userRepository.save(user);

        ConfirmationToken confirmationToken = new ConfirmationToken(user);
        confirmationTokenRepository.save(confirmationToken);

        String textMessage = null;

        if (activeProfile.equals("dev")) {
            textMessage = "To confirm your account, please click here: " +
                    "https://localhost:4200/confirmAccount/" + confirmationToken.getConfirmationToken();
        } else if (activeProfile.equals("prod")) {
            textMessage = "To confirm your account, please click here: " +
                    "https://oskarro.com/confirmAccount/" + confirmationToken.getConfirmationToken();
        }
        sendEmail("Complete Registration", "postmaster@oskarro.com", textMessage, user.getEmail());
        log.info("Activation email sent!!");

        return ResponseEntity.ok(
                new ApiResponse(true, "User registered successfully!"));
    }

    @Async
    void sendEmail(String subject, String sender, String text, String recipient) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(recipient);
        mailMessage.setSubject(subject);
        mailMessage.setFrom(sender);
        mailMessage.setText(text);
        emailService.sendEmail(mailMessage);
    }

    @RequestMapping(value="/confirmAccount/{token}", method= {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<?> confirmUserAccount(@PathVariable("token") String confirmationToken) {
        System.out.println("TOKEN: " + confirmationToken);

        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);

        if (token != null) {
            User user = userRepository.findByEmail(token.getUser().getEmail()).orElseThrow(
                    () -> new UsernameNotFoundException("User not found with email: " + token.getUser().getEmail())
            );
            user.setActivated(true);
            System.out.printf("USER %s ENABLED%n", user.getUsername());
            userRepository.save(user);
            initUserStatistics(user);
            sendEmail("Success confirmation", "postmaster@oskarro.com",
                    "Your email has been confirmed!", user.getEmail());
            return ResponseEntity.ok(new ApiResponse(true, "Token confirmed successfully!"));
        } else {
            throw new AppException("The link is invalid or broken!");
        }
    }

    @RequestMapping(value = "/token/{token}", method = RequestMethod.GET)
    public ResponseEntity<?> getUserByToken(@PathVariable String token) {
        User user = userDetailsService.getUserFromToken(token);
        Role roleUser = new Role();
        roleUser.setName(RoleName.ROLE_USER);
        List<String> roles = new ArrayList<>();
        roles.add(roleUser.toString());
        return ResponseEntity.ok(new JwtAuthenticationResponse(token,
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                roles));
    }

    /* RESET PASSWORD */
    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    public ResponseEntity<?> resetPassword(@RequestParam("email") final String email) {
        User existingUser = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email:" + email));

        ConfirmationToken confirmationToken = new ConfirmationToken(existingUser);
        confirmationTokenRepository.save(confirmationToken);

        String appUrl = "https://";
        if (activeProfile.equals("dev")) {
            appUrl += "localhost.com";
        } else if (activeProfile.equals("prod")) {
            appUrl += "oskarro.com";
        }

        sendEmail("Password Reset Request", "postmaster@oskarro.com",
                "To reset your password, click the link below:\n" + appUrl
                        + "/changePassword?token=" + confirmationToken.getConfirmationToken(), existingUser.getEmail());

        return ResponseEntity.ok(
                new ApiResponse(true, "Reset email has been sent"));
    }

    @RequestMapping(value = "/changePassword", method = RequestMethod.GET)
    public ResponseEntity<?> changePassword(@RequestParam("token") String token) {
        System.out.println("TOKEN: " + token);
        ConfirmationToken confirmationToken = confirmationTokenRepository.findByConfirmationToken(token);
        if (token != null) {
            User user = userRepository.findByEmail(confirmationToken.getUser().getEmail()).orElseThrow(
                    () -> new UsernameNotFoundException("User not found with email: " + confirmationToken.getUser().getEmail())
            );
            sendEmail("Success confirmation", "postmaster@oskarro.com",
                    "Your email has been confirmed!", user.getEmail());
            return ResponseEntity.ok(
                    new ApiResponse(true, "Token confirmed successfully!"));
        }
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new ApiResponse(false, "Could not change password. Wrong token!"));
    }

    /* CHANGE PASSWORD */
    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    public ResponseEntity<?> changeUserPassword(@Valid @RequestBody PasswordChangeDto passwordChangeDto) {
        final User user = userDetailsService
                .findUserByEmail(passwordChangeDto.getEmail());

        if (!userDetailsService.checkIfValidOldPassword(user, passwordChangeDto.getOldPassword())) {
            throw new InvalidOldPasswordException();
        }
        userDetailsService.changeUserPassword(user, passwordChangeDto.getNewPassword());
        sendEmail("Successful password change", "postmaster@oskarro.com",
                "Your password has been changed!", user.getEmail());
        return ResponseEntity.ok(
                new ApiResponse(true, "Password has been changed successfully!"));
    }

    private void initUserStatistics(User user) {
        UserStatistics userStatistics = UserStatistics
                .builder()
                .user(user)
                .weekUpload(0)
                .monthUpload(0)
                .totalUpload(0)
                .build();
        userStatisticsRepository.save(userStatistics);
    }

}
