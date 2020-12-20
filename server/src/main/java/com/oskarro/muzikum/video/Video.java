package com.oskarro.muzikum.video;

import com.oskarro.muzikum.playlist.Playlist;
import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.Instant;

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

    @Column(name = "comment_count")
    private Integer commentCount;

    @Column(name = "view_count")
    private Integer viewCount;

    @Column(name = "like_count")
    private Integer likeCount;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Instant updatedAt;

}
