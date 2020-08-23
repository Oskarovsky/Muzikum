package com.oskarro.muzikum.track.model;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrackPageResponse {
    List<Track> trackList;
    int totalPages;
    long totalElements;
    int numberPage;
}
