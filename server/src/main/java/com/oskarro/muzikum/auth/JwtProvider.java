package com.oskarro.muzikum.auth;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import org.springframework.security.core.Authentication;

@Slf4j
@Component
public class JwtProvider {

    @Value("${oskarro.app.jwtSecret}")
    private String jwtSecret;

    @Value("${oskarro.app.jwtExpiration}")
    private int jwtExpiration;

    private String generateJwtToken(Authentication authentication) {
    }

}
