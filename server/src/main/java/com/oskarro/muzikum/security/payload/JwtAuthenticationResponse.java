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
    private String email;
    private String username;
    private List<String> roles;

    public JwtAuthenticationResponse(String accessToken, Integer id, String username, String email,
                                     List<String> roles) {
        this.token = accessToken;
        this.username = username;
        this.email = email;
        this.id = id;
        this.roles = roles;
    }

}
