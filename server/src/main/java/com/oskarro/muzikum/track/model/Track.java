package com.oskarro.muzikum.track.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.oskarro.muzikum.playlist.Playlist;
import com.oskarro.muzikum.provider.Provider;
import com.oskarro.muzikum.record.Record;
import com.oskarro.muzikum.storage.Cover;
import com.oskarro.muzikum.user.User;
import com.oskarro.muzikum.utils.DateAudit;
import lombok.*;

import javax.persistence.*;

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

    private String urlSource;

    private String urlPlugin;

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
    @JoinColumn(name = "playlist_id")
    private Playlist playlist;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "cover_id")
    private Cover cover;
}
