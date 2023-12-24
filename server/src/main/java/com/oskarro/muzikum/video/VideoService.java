package com.oskarro.muzikum.video;

import com.oskarro.muzikum.playlist.Playlist;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.List;

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
