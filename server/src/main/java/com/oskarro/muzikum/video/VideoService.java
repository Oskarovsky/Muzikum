package com.oskarro.muzikum.video;

import com.oskarro.muzikum.playlist.Playlist;
import org.javatuples.Pair;
import org.javatuples.Tuple;

import java.util.List;
import java.util.Optional;

public interface VideoService {

    List<Video> getAllVideo();

    List<Video> findVideosByCategory(String category);

    void addVideo(Video video);

    Optional findVideoById(Integer id);

    void deleteVideoById(Integer id);

    Playlist getPlaylistFromVideoById(Integer id);

    void updateVideoStatistics();
}
