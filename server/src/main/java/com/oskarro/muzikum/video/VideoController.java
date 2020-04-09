package com.oskarro.muzikum.video;

import com.oskarro.muzikum.playlist.Playlist;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/video")
@CrossOrigin(origins = "http://localhost:4200")
public class VideoController {

    VideoService videoService;
    VideoRepository videoRepository;

    public VideoController(VideoService videoService, VideoRepository videoRepository) {
        this.videoService = videoService;
        this.videoRepository = videoRepository;
    }

    @GetMapping(value = "/findAll")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    List<Video> findAll() {
        return videoService.getAllVideo();
    }

    @GetMapping(value = "/{id}")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    Optional<Video> getVideoById(@PathVariable Integer id) {
        return videoService.findVideoById(id);
    }

    @PostMapping(value = "/add")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public void addVideo(@RequestBody Video video) {
        videoService.addVideo(video);
    }

    @DeleteMapping(value = "/{id}")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public void delete(@PathVariable Integer id) {
        this.videoRepository.deleteById(videoRepository.findById(id).get().getId());
    }

}
