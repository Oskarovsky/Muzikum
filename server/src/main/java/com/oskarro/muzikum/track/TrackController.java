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
    List<Track> findAll() {
        return trackService.findAll();
    }

    @GetMapping(value = "/{id}")
    Optional<Track> findById(@PathVariable Integer id) {
        return trackService.findById(id);
    }

    @PostMapping(value = "/add")
    public void addTrack(@RequestBody Track track) {
        trackService.saveTrack(track);
    }

}
