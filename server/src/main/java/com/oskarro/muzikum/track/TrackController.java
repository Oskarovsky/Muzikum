package com.oskarro.muzikum.track;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/tracks")
public class TrackController {

    private final TrackService trackService;

    public TrackController(TrackService trackService) {
        this.trackService = trackService;
    }

    @GetMapping(value = "/findAll")
    @CrossOrigin(origins = "http://localhost:4200")
    List<Track> findAll() {
        return trackService.findAll();
    }

    @GetMapping(value = "/id/{id}")
    Optional<Track> findById(@PathVariable Integer id) {
        return trackService.findById(id);
    }

    @PostMapping(value = "/add")
    public void addTrack(@RequestBody Track track) {
        trackService.saveTrack(track);
    }

    @GetMapping(value = "/genre/{genre}")
    List<Track> findByGenre(@PathVariable String genre) {
        return trackService.findTracksByGenre(genre);
    }

}
