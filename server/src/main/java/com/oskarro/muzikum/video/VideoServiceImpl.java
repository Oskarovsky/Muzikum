package com.oskarro.muzikum.video;

import com.oskarro.muzikum.exception.ResourceNotFoundException;
import com.oskarro.muzikum.playlist.Playlist;
import org.apache.commons.io.IOUtils;
import org.javatuples.Pair;
import org.javatuples.Tuple;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.UTF_8;

@Service
public class VideoServiceImpl implements VideoService {

    VideoRepository videoRepository;


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
    }

    public void updateYoutubeInformation(String videoUrl) throws IOException, ParseException {
        Object object = new JSONParser().parse(IOUtils.toString(new URL(videoUrl), UTF_8));
        JSONObject jsonObject = (JSONObject) object;
        Integer val1 = (Integer) jsonObject.get("statistics.viewCount");
        Video video = videoRepository.findByUrl(videoUrl)
                .orElseThrow(() -> new ResourceNotFoundException("Video", "videoUrl", videoUrl));
        video.setViewCount(val1);
        videoRepository.save(video);
    }
}
