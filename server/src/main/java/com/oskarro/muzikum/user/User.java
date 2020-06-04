package com.oskarro.muzikum.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.oskarro.muzikum.track.Track;
import com.oskarro.muzikum.user.role.Role;
import com.oskarro.muzikum.utils.DateAudit;
import lombok.*;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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

    @NotNull
    @Size(min=3, max = 32)
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @NaturalId
    @NotNull
    @Size(max = 50)
    @Email
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NotNull
    @Size(min=6, max = 100)
    @JsonIgnore
    @Column(name = "password", nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @JsonIgnore
    @Column(name = "enabled")
    private boolean isEnabled;

    @Column(name = "created_on")
    private Date createdOn;

    @JsonIgnore
    @Column(name = "last_login")
    private Date lastLogin;

    @JsonIgnore
    @Column(name = "reset_token")
    private String resetToken;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "favorite_track")
    private Track favoriteTrack;

//    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
//    @JoinTable(name = "users_favorite_tracks",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "track_id"))
//    private Set<Track> favoriteTracks = new HashSet<>();
//
//    public void addFavoriteTrack(Track track) {
//        if (this.favoriteTracks == null) {
//            this.favoriteTracks = new HashSet<Track>();
//        }
//        this.favoriteTracks.add(track);
//        if (track.getFavoriteUsers() == null) {
//            track.setFavoriteUsers(new HashSet<User>());
//        }
//        track.getFavoriteUsers().add(this);
//    }
//
//    public void removeFavoriteTrack(Track track) {
//        this.favoriteTracks.remove(track);
//        track.getFavoriteUsers().remove(this);
//    }

}
