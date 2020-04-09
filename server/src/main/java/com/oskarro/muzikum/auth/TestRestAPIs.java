package com.oskarro.muzikum.auth;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/api/test")
public class TestRestAPIs {

    @GetMapping(value = "/all")
    public String allAccess() {
        return "Public Content.";
    }

    @GetMapping(value = "/user")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public String userAccess() {
        return ">>> User Contents!";
    }

    @GetMapping(value = "/mod")
    @PreAuthorize("hasRole('MODERATOR')")
    public String projectManagementAccess() {
        return ">>> Moderator Board";
    }

    @GetMapping(value = "/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        return ">>> Admin Contents";
    }
}
