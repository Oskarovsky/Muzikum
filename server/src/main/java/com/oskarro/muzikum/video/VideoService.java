package com.oskarro.muzikum.video;

import com.oskarro.muzikum.playlist.Playlist;
import org.javatuples.Pair;
import org.javatuples.Tuple;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface VideoService {

    List<Video> getAllVideo();

    List<Video> findVideosByCategory(String category);

    void addVideo(Video video);

    Video findVideoById(Integer id);

    void deleteById(Integer id);

    Playlist getPlaylistFromVideoById(Integer id);

    void updateVideoStatistics();

    void updateYoutubeInformation(String videoUrl) throws IOException, ParseException;

    List<Video> getTop10PopularVideos();

    List<Video> getAllVideosSortedByViews();
}
