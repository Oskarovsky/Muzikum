package com.oskarro.muzikum.track;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/tracks")
public class TrackController {

    private final TrackService trackService;

    public TrackController(TrackService trackService) {
        this.trackService = trackService;
    }

    //@GetMapping
    public List<Track> getTracks() {
        List<Track> trackList = new ArrayList<>();
        Track track = Track.builder().title("aaa").artist("bbb").genre("club").points(31).id(1).build();
        Track track1 = Track.builder().title("aa2a").artist("b32bb").genre("dance").points(11).id(2).build();
        Track track2 = Track.builder().title("aa34a").artist("bb34b").genre("club").points(55).id(3).build();
        trackList.add(track);
        trackList.add(track1);
        trackList.add(track2);
        return trackList;
    }

    @GetMapping
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
