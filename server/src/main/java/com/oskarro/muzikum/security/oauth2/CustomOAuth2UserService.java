package com.oskarro.muzikum.security.oauth2;

import com.oskarro.muzikum.exception.OAuth2AuthenticationProcessingException;
import com.oskarro.muzikum.exception.ResourceNotFoundException;
import com.oskarro.muzikum.storage.Image;
import com.oskarro.muzikum.storage.ImageRepository;
import com.oskarro.muzikum.user.*;
import com.oskarro.muzikum.user.role.Role;
import com.oskarro.muzikum.user.role.RoleName;
import com.oskarro.muzikum.user.role.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.security.core.AuthenticationException;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * The CustomOAuth2UserService extends Spring Security’s DefaultOAuth2UserService and implements its loadUser() method.
 * This method is called after an access token is obtained from the OAuth2 provider.
 *
 * In this method, we first fetch the user’s details from the OAuth2 provider.
 * If a user with the same email already exists in our database then we update his details, otherwise, we register a new user.
 * */

@Slf4j
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final UserStatisticsRepository userStatisticsRepository;

    private final PasswordEncoder passwordEncoder;

    final ImageRepository imageRepository;

    public CustomOAuth2UserService(UserRepository userRepository,
                                   RoleRepository roleRepository,
                                   UserStatisticsRepository userStatisticsRepository,
                                   PasswordEncoder passwordEncoder,
                                   ImageRepository imageRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userStatisticsRepository = userStatisticsRepository;
        this.passwordEncoder = passwordEncoder;
        this.imageRepository = imageRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(request);
        try {
            return processOAuth2User(request, oAuth2User);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            // Throwing an instance of AuthenticationException will trigger the OAuth2AuthenticationFailureHandler
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest request, OAuth2User oAuth2User) {
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory
                .getOAuth2UserInfo(request.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());

        Optional<User> userOptional = userRepository.findByEmail(oAuth2UserInfo.getEmail());
        User user;
        if (userOptional.isPresent()) {
            user = userOptional.get();
            if (!user.getProvider().equals(AuthProvider.valueOf(request.getClientRegistration().getRegistrationId()))) {
                throw new OAuth2AuthenticationProcessingException(
                        String.format("Looks like you're signed up with %s account. Please use your %s account to login",
                                user.getProvider(), user.getProvider()));
            }
            user = updateExistingUser(user, oAuth2UserInfo);
        } else {
            user = registerNewUser(request, oAuth2UserInfo);
        }
        return UserPrincipal.create(user, oAuth2User.getAttributes());
    }

    private User registerNewUser(OAuth2UserRequest request, OAuth2UserInfo oAuth2UserInfo) {
        Role roleUser = roleRepository.findByName(RoleName.ROLE_USER).orElse(null);
        User user = User.builder()
                .username(oAuth2UserInfo.getUsername())
                .email(oAuth2UserInfo.getEmail())
                .password(passwordEncoder.encode("123456"))
                .roles(new HashSet<>(Collections.singletonList(roleUser)))
                .provider(AuthProvider.facebook)
                .imageUrl(oAuth2UserInfo.getImageUrl())
                .build();
        initUserStatistics(user);
        saveImageFromWeb(oAuth2UserInfo.getEmail(), oAuth2UserInfo.getImageUrl());
        return userRepository.save(user);
    }

    private void saveImageFromWeb(String email, String imageUrl) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
    }

    private User updateExistingUser(User existingUser, OAuth2UserInfo oAuth2UserInfo) {
        existingUser.setUsername(oAuth2UserInfo.getUsername());
        existingUser.setImageUrl(oAuth2UserInfo.getImageUrl());
        return userRepository.save(existingUser);
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
