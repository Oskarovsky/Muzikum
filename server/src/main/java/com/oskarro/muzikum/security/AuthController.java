package com.oskarro.muzikum.security;

import com.oskarro.muzikum.security.payload.JwtAuthenticationResponse;
import com.oskarro.muzikum.security.payload.ApiResponse;
import com.oskarro.muzikum.security.payload.LoginRequest;
import com.oskarro.muzikum.security.payload.RegisterRequest;

import javax.validation.Valid;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static java.time.Instant.now;

@Slf4j
@RestController
@RequestMapping(value = "/api/auth")
@CrossOrigin
@AllArgsConstructor
public class AuthController {

    final AuthService authService;

    @PostMapping(value = "/signin")
    public ResponseEntity<JwtAuthenticationResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        JwtAuthenticationResponse jwtAuthenticationResponse = authService.authenticateUser(loginRequest);
        return ResponseEntity.ok(jwtAuthenticationResponse);
    }

    @PostMapping(value = "/signup")
    public ResponseEntity<ApiResponse> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        ApiResponse apiResponse = authService.registerUser(registerRequest);
        return ResponseEntity.ok(apiResponse);
    }

    @RequestMapping(value="/confirmAccount/{token}", method= {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<ApiResponse> confirmUserAccount(@PathVariable("token") String confirmationToken) {
        ApiResponse apiResponse = authService.confirmUserAccount(confirmationToken);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping(value = "/token/{token}")
    public ResponseEntity<JwtAuthenticationResponse> getUserByToken(@PathVariable String token) {
        JwtAuthenticationResponse jwtAuthenticationResponse = authService.getJwtAuthenticationResponse(token);
        return ResponseEntity.ok(jwtAuthenticationResponse);
    }

    /* RESET PASSWORD */
    @PostMapping(value = "/resetPassword")
    public ResponseEntity<ApiResponse> resetPassword(@RequestParam("email") final String email) {
        ApiResponse apiResponse = authService.sendPasswordResetRequestToUser(email);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping(value = "/changePassword")
    public ResponseEntity<ApiResponse> changePasswordAfterReset(@RequestParam("token") String token) {
        ApiResponse apiResponse = authService.changePasswordAfterReset(token);
        if (Boolean.TRUE.equals(apiResponse.getSuccess())) {
            return ResponseEntity.ok(apiResponse);
        } else {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(new ApiResponse(false, "Could not change password. Wrong token!"));
        }
    }

    /* CHANGE PASSWORD */
    @PostMapping(value = "/updatePassword")
    public ResponseEntity<ApiResponse> updateUserPassword(@Valid @RequestBody PasswordChangeDto passwordChangeDto) {
        ApiResponse apiResponse = authService.changeUserPassword(passwordChangeDto);
        return ResponseEntity.ok(apiResponse);
    }

}
