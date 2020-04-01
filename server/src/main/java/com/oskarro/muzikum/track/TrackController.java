package com.oskarro.muzikum.track;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.ValidationException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/tracks")
@CrossOrigin
public class TrackController {

    private final TrackService trackService;

    public TrackController(TrackService trackService) {
        this.trackService = trackService;
    }

    @GetMapping(value = "/findAll")
    List<Track> findAll() {
        return trackService.findAll();
    }

    @GetMapping(value = "/id/{id}")
    Optional<Track> findById(@PathVariable Integer id) {
        return trackService.findById(id);
    }

    @PostMapping(value = "/add")
    void addTrack(@RequestBody Track track) {
        trackService.saveTrack(track);
    }

    @GetMapping(value = "/genre/{genre}")
    List<Track> findByGenre(@PathVariable String genre) {
        return trackService.findTracksByGenre(genre);
    }

    @PostMapping(value = "/addToRanking")
    void addTrackToRanking (@RequestBody Track track, BindingResult bindingResult) throws ValidationException {
        if (bindingResult.hasErrors()) {
            throw new ValidationException("Track has errors - it cannot by send");
        }
        Track track1 = Track.builder()
                .artist(track.getArtist())
                .title(track.getTitle())
                .version(track.getVersion())
                .build();
        trackService.saveTrack(track1);
    }

}
