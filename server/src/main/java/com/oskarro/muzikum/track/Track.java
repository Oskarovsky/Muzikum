package com.oskarro.muzikum.track;

import com.oskarro.muzikum.playlist.Playlist;
import com.oskarro.muzikum.provider.Provider;
import com.oskarro.muzikum.record.Record;
import com.oskarro.muzikum.user.User;
import com.oskarro.muzikum.video.Video;
import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Track {

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
    @JoinColumn(name = "provider_id")
    private Provider provider;

    @ManyToOne
    @JoinColumn(name = "record_id")
    private Record record;

    @ManyToOne
    @JoinColumn(name = "playlist_id")
    private Playlist playlist;

    @ManyToOne
    @JoinColumn(name = "video_id")
    private Video video;


//    @ManyToMany(mappedBy = "favoriteTracks")
//    private Set<User> favoriteUsers = new HashSet<>();


}
