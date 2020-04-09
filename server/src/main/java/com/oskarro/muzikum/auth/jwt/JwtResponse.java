package com.oskarro.muzikum.auth.jwt;

import java.util.Collection;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@Getter
@Setter
public class JwtResponse {
    private Integer id;
    private String token;
    private String type = "Bearer";
    private String username;
    private String email;
    private Collection<? extends GrantedAuthority> authorities;

    public JwtResponse(String accessToken, Integer id, String username, String email,
                       Collection<? extends GrantedAuthority> authorities) {
        this.token = accessToken;
        this.username = username;
        this.authorities = authorities;
        this.email = email;
        this.id = id;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }


}
