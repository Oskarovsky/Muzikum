package com.oskarro.muzikum.playlist;

import com.oskarro.muzikum.user.User;
import com.oskarro.muzikum.utils.DateAudit;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Playlist extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Integer points;

    private Integer views;

}
