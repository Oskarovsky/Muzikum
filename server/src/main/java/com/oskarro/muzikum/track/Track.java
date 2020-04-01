package com.oskarro.muzikum.track;

import com.oskarro.muzikum.playlist.Playlist;
import com.oskarro.muzikum.provider.Provider;
import com.oskarro.muzikum.record.Record;
import lombok.*;

import javax.persistence.*;
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



}
