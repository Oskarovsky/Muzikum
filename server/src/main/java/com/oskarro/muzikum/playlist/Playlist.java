package com.oskarro.muzikum.playlist;

import com.oskarro.muzikum.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Integer points;

}
