package com.oskarro.muzikum.video;

import com.oskarro.muzikum.playlist.Playlist;
import lombok.*;

import javax.persistence.*;

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

    private String category;

    @OneToOne
    @JoinColumn(name = "playlist_id")
    private Playlist playlist;

}
