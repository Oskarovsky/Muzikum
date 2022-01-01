package com.oskarro.muzikum.video;

import com.oskarro.muzikum.playlist.Playlist;
import com.oskarro.muzikum.track.model.Track;
import com.oskarro.muzikum.track.TrackService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.ValidationException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/video")
@CrossOrigin
public class VideoController {

    private final VideoService videoService;
    private final VideoRepository videoRepository;
    private final TrackService trackService;

    public VideoController(VideoService videoService, VideoRepository videoRepository,
                           TrackService trackService) {
        this.videoService = videoService;
        this.videoRepository = videoRepository;
        this.trackService = trackService;
    }

    @GetMapping(value = "/findAll")
    List<Video> findAll() {
        return videoService.getAllVideo();
    }

    @GetMapping(value = "/{id}")
    Optional<Video> getVideoById(@PathVariable Integer id) {
        return videoService.findVideoById(id);
    }

    @PostMapping(value = "/add")
    public void addVideo(@RequestBody Video video, BindingResult bindingResult) throws ValidationException {
        if (bindingResult.hasErrors()) {
            throw new ValidationException("Video has errors - it cannot by send");
        }
        Video videoAdded = Video.builder()
                .name(video.getName())
                .category(video.getCategory())
                .url(video.getUrl())
                .build();
        videoService.addVideo(videoAdded);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable Integer id) {
        this.videoRepository.deleteById(videoRepository.findById(id).get().getId());
    }

    @GetMapping(value = "/findAll/{category}")
    public List<Video> getVideosByCategory(@PathVariable String category) {
        return videoService.findVideosByCategory(category);
    }

    @GetMapping(value = "/{id}/tracks")
    public List<Track> getAllTracksFromVideo(@PathVariable Integer id) {
        return trackService.findAllTracksFromVideo(id);
    }

    @GetMapping(value = "/{id}/playlist")
    public Playlist getPlaylistFromVideo(@PathVariable Integer id) {
        return videoService.getPlaylistFromVideoById(id);
    }

    @GetMapping(value = "/top")
    public List<Video> getTopVideos() {
        return videoService.getTop10PopularVideos();
    }

    @GetMapping(value = "/all/sorted")
    public List<Video> getAllVideosSortedByViews() {
        return videoService.getAllVideosSortedByViews();
    }

}
