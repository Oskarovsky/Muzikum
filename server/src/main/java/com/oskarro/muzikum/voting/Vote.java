package com.oskarro.muzikum.voting;

import com.oskarro.muzikum.track.model.Track;
import com.oskarro.muzikum.user.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private VoteType voteType;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "track_id")
    private Track track;

}
