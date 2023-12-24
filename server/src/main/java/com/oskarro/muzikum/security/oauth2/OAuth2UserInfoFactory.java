package com.oskarro.muzikum.security.oauth2;

import com.oskarro.muzikum.exception.OAuth2AuthenticationProcessingException;
import com.oskarro.muzikum.user.AuthProvider;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Map;

public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if (registrationId.equalsIgnoreCase(AuthProvider.google.toString())) {
            return new GoogleOAuth2UserInfo(attributes);
        } else if (registrationId.equalsIgnoreCase(AuthProvider.facebook.toString())) {
            return new FacebookOAuth2UserInfo(attributes);
        } else {
            throw new OAuth2AuthenticationProcessingException("Sorry! Login with " + registrationId + " is not supported yet.");
        }
    }
}
