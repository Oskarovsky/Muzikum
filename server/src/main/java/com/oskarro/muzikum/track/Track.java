package com.oskarro.muzikum.track;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.oskarro.muzikum.playlist.Playlist;
import com.oskarro.muzikum.provider.Provider;
import com.oskarro.muzikum.record.Record;
import com.oskarro.muzikum.user.User;
import com.oskarro.muzikum.utils.DateAudit;
import com.oskarro.muzikum.video.Video;
import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Track extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    private String artist;

    private Integer points;

    private String genre;

    private String version;

    private String url;

    private Integer position;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "provider_id")
    private Provider provider;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "record_id")
    private Record record;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "playlist_id")
    private Playlist playlist;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "video_id")
    private Video video;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id")
    User user;



//    @ManyToMany(mappedBy = "favoriteTracks")
//    private Set<User> favoriteUsers = new HashSet<>();


}
