package com.oskarro.muzikum.user.favorite;

import com.oskarro.muzikum.track.model.Track;
import com.oskarro.muzikum.user.User;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteTrack {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "favorite_id")
    private Track track;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
