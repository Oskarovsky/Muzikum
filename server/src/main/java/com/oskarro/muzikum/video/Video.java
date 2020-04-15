package com.oskarro.muzikum.video;

import com.oskarro.muzikum.track.Track;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    private String name;

    @NonNull
    private String url;

    @OneToMany
    private Set<Track> tracks;

    private String category;

}
