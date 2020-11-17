package com.oskarro.muzikum.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A DTO representing a password change required data - current and new password.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PasswordChangeDto {
    private String email;
    private String oldPassword;
    private String newPassword;
}
