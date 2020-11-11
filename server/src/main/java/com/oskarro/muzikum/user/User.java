package com.oskarro.muzikum.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.oskarro.muzikum.track.model.Track;
import com.oskarro.muzikum.user.role.Role;
import com.oskarro.muzikum.utils.DateAudit;
import lombok.*;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.Instant;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "email")
        })
public class User extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Integer id;

    @NotBlank(message = "Username is required")
    @Size(min=3, max = 32)
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @NaturalId
    @NotEmpty(message = "Email is required")
    @Size(max = 50)
    @Email
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min=6, max = 100)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "password", nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @Size(max = 256)
    @Column(name = "image_url")
    private String imageUrl;

    @Size(max = 20)
    @Column(name = "activation_key")
    @JsonIgnore
    private String activationKey;

    @Size(max = 20)
    @Column(name = "reset_key")
    @JsonIgnore
    private String resetKey;

    @Column(name = "reset_date")
    private Instant resetDate = null;

    @JsonIgnore
    @Column(name = "activated")
    private boolean activated;

    @Column(name = "created_date")
    private Date createdDate;

    @JsonIgnore
    @Column(name = "last_login_date")
    private Date lastLoginDate;

    @JsonIgnore
    @Column(name = "reset_token")
    private String resetToken;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "favorite_track")
    private Track favoriteTrack;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    private String providerId;

}
