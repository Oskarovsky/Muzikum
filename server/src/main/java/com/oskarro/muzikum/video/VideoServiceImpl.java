package com.oskarro.muzikum.video;

import com.oskarro.muzikum.exception.ResourceNotFoundException;
import com.oskarro.muzikum.playlist.Playlist;
import com.oskarro.muzikum.playlist.PlaylistRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.UTF_8;

@Slf4j
@Service
public class VideoServiceImpl implements VideoService {

    private final VideoRepository videoRepository;
    private final PlaylistRepository playlistRepository;

    @Value("${google.api.key}")
    private String GOOGLE_API_KEY;

    public VideoServiceImpl(final VideoRepository videoRepository,
                            final PlaylistRepository playlistRepository) {
        this.videoRepository = videoRepository;
        this.playlistRepository = playlistRepository;
    }

    @Override
    public List<Video> getAllVideo() {
        return videoRepository.findAll();
    }

    @Override
    public List<Video> findVideosByCategory(final String category) {
        return videoRepository.findVideosByCategory(category);
    }

    @Override
    public void addVideo(Video video) {
        Playlist playlistBuild = Playlist.builder().name("Tracklist " + video).points(0).build();
        Playlist result = playlistRepository.save(playlistBuild);
        video.setPlaylist(result);
        videoRepository.save(video);
    }

    @Override
    public Video findVideoById(Integer videoId) {
        return videoRepository
                .findById(videoId)
                .orElseThrow(() -> new ResourceNotFoundException("Video", "id", videoId));
    }

    @Override
    public void deleteById(final Integer id) {
        videoRepository.deleteById(id);
    }

    @Override
    public Playlist getPlaylistFromVideoById(Integer id) {
        Video video = videoRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Fail! -> Cause: Video cannot be found"));
        return video.getPlaylist();
    }

    @Override
    public void updateVideoStatistics() {
        videoRepository
                .findAll()
                .stream()
                .map(Video::getUrl)
                .forEach(t -> {
                    try {
                        updateYoutubeInformation(t);
                    } catch (IOException | ParseException e) {
                        e.printStackTrace();
                    }
                });
    }

    @Override
    public void updateYoutubeInformation(String videoUrl) throws IOException, ParseException {
        String jsonPart = "statistics";
        String youtubeApiUrl = "https://www.googleapis.com/youtube/v3/videos" +
                "?part=" + jsonPart +
                "&id=" + videoUrl +
                "&key=" + GOOGLE_API_KEY;
        Object object = new JSONParser().parse(IOUtils.toString(new URL(youtubeApiUrl), UTF_8));
        JSONObject jsonObject = (JSONObject) object;
        JSONArray itemsPartFromJson = (JSONArray) jsonObject.get("items");
        if (!itemsPartFromJson.isEmpty()) {
            JSONObject itemsPartJsonObject = (JSONObject) itemsPartFromJson.get(0);
            JSONObject statisticsPartJsonObject = (JSONObject) itemsPartJsonObject.get("statistics");
            String viewCount = String.valueOf(statisticsPartJsonObject.get("viewCount"));
            String commentCount = String.valueOf(statisticsPartJsonObject.get("commentCount"));
            String likeCount = String.valueOf(statisticsPartJsonObject.get("likeCount"));
            Video video = videoRepository.findByUrl(videoUrl)
                    .orElseThrow(() -> new ResourceNotFoundException("Video", "videoUrl", videoUrl));
            video.setViewCount(Integer.valueOf(viewCount));
            video.setCommentCount(Integer.valueOf(commentCount));
            video.setLikeCount(Integer.valueOf(likeCount));
            video.setUpdatedAt(Instant.now());
            videoRepository.save(video);
            log.info("Video statistics for {} has been updated --> views: {}, likes: {}, comments: {}",
                    video.getName(), viewCount, likeCount, commentCount);
        } else {
            log.warn("Video statistics with url {} cannot be updated", videoUrl);
        }
    }

    @Override
    public List<Video> getTop10PopularVideos() {
        return videoRepository.findTop10ByOrderByViewCountDesc();
    }

    @Override
    public List<Video> getAllVideosSortedByViews() {
        return videoRepository.findAllByOrderByViewCountDesc();
    }
}
