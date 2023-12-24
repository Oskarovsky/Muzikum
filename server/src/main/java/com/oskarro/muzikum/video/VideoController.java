package com.oskarro.muzikum.video;

import com.oskarro.muzikum.playlist.Playlist;
import com.oskarro.muzikum.track.TrackService;
import com.oskarro.muzikum.track.model.Track;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.ValidationException;
import java.util.List;

@RestController
@RequestMapping(value = "/api/video")
@CrossOrigin
public class VideoController {

    private final VideoService videoService;
    private final TrackService trackService;

    public VideoController(final VideoService videoService,
                           final TrackService trackService) {
        this.videoService = videoService;
        this.trackService = trackService;
    }

    @GetMapping
    List<Video> findAll() {
        return videoService.getAllVideo();
    }

    @GetMapping(value = "/{videoId}")
    Video getVideoById(@PathVariable final Integer videoId) {
        return videoService.findVideoById(videoId);
    }

    @PostMapping
    public void addVideo(@RequestBody final Video video,
                         final BindingResult bindingResult) throws ValidationException {
        if (bindingResult.hasErrors()) {
            throw new ValidationException("Video has errors - it cannot by send");
        }
        videoService.addVideo(video);
    }

    @DeleteMapping(value = "/{videoId}")
    public void delete(@PathVariable final Integer videoId) {
        videoService.deleteById(videoId);
    }

    @GetMapping(value = "/category/{category}")
    public List<Video> getVideosByCategory(@PathVariable final String category) {
        return videoService.findVideosByCategory(category);
    }

    @GetMapping(value = "/{id}/tracks")
    public List<Track> getAllTracksFromVideo(@PathVariable final Integer id) {
        return trackService.findAllTracksFromVideo(id);
    }

    @GetMapping(value = "/{id}/playlist")
    public Playlist getPlaylistFromVideo(@PathVariable final Integer id) {
        return videoService.getPlaylistFromVideoById(id);
    }

    @GetMapping(value = "/top")
    public List<Video> getTopVideos() {
        return videoService.getTop10PopularVideos();
    }

    @GetMapping(value = "/sorted")
    public List<Video> getAllVideosSortedByViews() {
        return videoService.getAllVideosSortedByViews();
    }

}
