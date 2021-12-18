package com.oskarro.muzikum.track.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrackDto {
    private Integer id;
    private String title;
    private String artist;
    private Integer points;
    private String genre;
    private String version;
    private String url;
    private Integer position;

    private Integer providerId;
    private Integer recordId;
    private Integer playlistId;
    private Integer videoId;
    private Integer userId;
}
