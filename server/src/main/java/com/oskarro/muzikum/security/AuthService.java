package com.oskarro.muzikum.security;

import com.oskarro.muzikum.exception.AppException;
import com.oskarro.muzikum.exception.InvalidOldPasswordException;
import com.oskarro.muzikum.security.jwt.JwtTokenProvider;
import com.oskarro.muzikum.security.payload.ApiResponse;
import com.oskarro.muzikum.security.payload.JwtAuthenticationResponse;
import com.oskarro.muzikum.security.payload.LoginRequest;
import com.oskarro.muzikum.security.payload.RegisterRequest;
import com.oskarro.muzikum.user.*;
import com.oskarro.muzikum.email.EmailService;
import com.oskarro.muzikum.user.role.Role;
import com.oskarro.muzikum.user.role.RoleName;
import com.oskarro.muzikum.user.role.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class AuthService {

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

    @Value("${email.sender}")
    private String emailSender;

    public AuthService(AuthenticationManager authenticationManager,
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

    JwtAuthenticationResponse authenticateUser(LoginRequest loginRequest) {

        log.info("Authentication user with username: {}", loginRequest.getUsernameOrEmail());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtTokenProvider.generateJwtToken(authentication);
        UserPrincipal userDetails = (UserPrincipal) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        log.info("User {} has been authenticated.", userDetails.getEmail());
        return new JwtAuthenticationResponse(jwt,
                userDetails.getId(),
                userDetails.getEmail(),
                userDetails.getUsername(),
                roles);
    }

    ApiResponse registerUser(RegisterRequest registerRequest) {
        log.info("Registering new user with email {}", registerRequest.getEmail());
        if (Boolean.TRUE.equals(userRepository.existsByUsername(registerRequest.getUsername()))) {
            return new ApiResponse(false,"Operation failed. Username is already taken!");
        } else if (Boolean.TRUE.equals(userRepository.existsByEmail(registerRequest.getEmail()))) {
            return new ApiResponse(false,"Operation failed. Email is already in use!");
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
                    .orElseThrow(() -> new RuntimeException("Error: Cannot find user role in database."));
            roles.add(userRole);
        } else {
            stringRoles.forEach(role -> {
                switch (role) {
                    case "admin" -> {
                        Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Fail! -> Cause: Admin Role not found."));
                        roles.add(adminRole);
                    }
                    case "mod" -> {
                        Role modRole = roleRepository.findByName(RoleName.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Fail! -> Cause: Mod Role not find."));
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
        log.info("New user {} has been saved in database", user.getEmail());

        sendConfirmationEmailWithToken(user);
        return new ApiResponse(true, "User registered successfully!");
    }

    public void sendConfirmationEmailWithToken(User user) {
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
        emailService.sendEmailToUser("Complete Registration", emailSender, textMessage, user.getEmail());
        log.info("Activation email has been sent to: {}", user.getEmail());
    }

    ApiResponse confirmUserAccount(String confirmationToken) {
        log.info("Passing confirmation token: {}", confirmationToken);

        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);
        if (token != null) {
            User user = userRepository.findByEmail(token.getUser().getEmail()).orElseThrow(
                    () -> new UsernameNotFoundException("User not found with email: " + token.getUser().getEmail())
            );
            user.setActivated(true);
            userRepository.save(user);
            log.info("User {} has just been activated.", user.getUsername());
            initUserStatistics(user);
            emailService.sendEmailToUser("Success confirmation", emailSender,
                    "Your email has been confirmed!", user.getEmail());
            return new ApiResponse(true, "Token confirmed successfully!");
        } else {
            throw new AppException("The link is invalid or broken!");
        }
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

    JwtAuthenticationResponse getJwtAuthenticationResponse(String token) {
        User user = userDetailsService.getUserFromToken(token);
        Role roleUser = new Role();
        roleUser.setName(RoleName.ROLE_USER);
        List<String> roles = new ArrayList<>();
        roles.add(roleUser.toString());
        return new JwtAuthenticationResponse(token,
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                roles);
    }

    ApiResponse sendPasswordResetRequestToUser(String email) {
        log.info("Sending message with reset password request for user with email {}", email);
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

        emailService.sendEmailToUser("Password Reset Request", emailSender,
                "To reset your password, click the link below:\n" + appUrl
                        + "/changePassword?token=" + confirmationToken.getConfirmationToken(), existingUser.getEmail());

        return new ApiResponse(true, "Reset email has been sent");
    }

    ApiResponse changePasswordAfterReset(String token) {
        log.info("Changing user password using TOKEN: {}", token);
        ConfirmationToken confirmationToken = confirmationTokenRepository.findByConfirmationToken(token);
        if (token != null) {
            User user = userRepository.findByEmail(confirmationToken.getUser().getEmail()).orElseThrow(
                    () -> new UsernameNotFoundException("User not found with email: " + confirmationToken.getUser().getEmail())
            );
            emailService.sendEmailToUser("Success confirmation", emailSender,
                    "Your email has been confirmed!", user.getEmail());
            return new ApiResponse(true, "Token confirmed successfully!");
        }
        return new ApiResponse(false, "Could not change password.");
    }

    ApiResponse changeUserPassword(PasswordChangeDto passwordChangeDto) {
        final User user = userDetailsService
                .findUserByEmail(passwordChangeDto.getEmail());

        if (!userDetailsService.checkIfValidOldPassword(user, passwordChangeDto.getOldPassword())) {
            throw new InvalidOldPasswordException();
        }
        userDetailsService.changeUserPassword(user, passwordChangeDto.getNewPassword());
        emailService.sendEmailToUser("Successful password change", emailSender,
                "Your password has been changed!", user.getEmail());

        return new ApiResponse(true, "Password has been changed successfully!");
    }
}
