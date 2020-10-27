package com.oskarro.muzikum.security.payload;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtAuthenticationResponse {
    private Integer id;
    private String token;
    private String type = "Bearer";
    private String username;
    private String email;
    private List<String> roles;

    public JwtAuthenticationResponse(String accessToken, Integer id, String username, String email,
                                     List<String> roles) {
        this.token = accessToken;
        this.username = username;
        this.roles = roles;
        this.email = email;
        this.id = id;
    }

}
