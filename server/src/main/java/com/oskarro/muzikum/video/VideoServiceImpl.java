package com.oskarro.muzikum.video;

import com.oskarro.muzikum.exception.ResourceNotFoundException;
import com.oskarro.muzikum.playlist.Playlist;
import org.apache.commons.io.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;


import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


import static java.nio.charset.StandardCharsets.UTF_8;

@Slf4j
@Service
public class VideoServiceImpl implements VideoService {

    VideoRepository videoRepository;

    @Value("${google.api.key}")
    private String GOOGLE_API_KEY;


    public VideoServiceImpl(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    @Override
    public List<Video> getAllVideo() {
        return videoRepository.findAll();
    }

    @Override
    public List<Video> findVideosByCategory(String category) {
        return videoRepository.findVideosByCategory(category);
    }

    @Override
    public void addVideo(Video video) {
        videoRepository.save(video);
    }

    @Override
    public Optional<Video> findVideoById(Integer id) {
        return videoRepository.findById(id);
    }

    @Override
    public void deleteVideoById(Integer id) {
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
        List<String> videoUrls = videoRepository.findAll().stream().map(Video::getUrl).collect(Collectors.toList());
        for (String url: videoUrls) {
            try {
                updateYoutubeInformation(url);
            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }
        }
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
}
