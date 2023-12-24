package com.oskarro.muzikum.user;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * A DTO representing a user, with his authorities.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Integer id;

    @NotBlank
    @Size(min = 1, max = 50)
    private String username;

    @Email
    @Size(min = 5, max = 254)
    private String email;

    private Set<String> roles = new HashSet<>();

    @Size(max = 256)
    private String imageUrl;

    private String firstName;

    private String city;

    private String facebookUrl;

    private String youtubeUrl;


}
