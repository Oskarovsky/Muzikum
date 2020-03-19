package com.oskarro.muzikum.track;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/tracks")
public class TrackController {

    private final TrackService trackService;

    private TrackRepository trackRepository;

    @GetMapping(value = "initTracks")
    public String getTracks() {
        trackRepository.saveAll(Arrays.asList(
                Track.builder().title("aaa").artist("bbb").genre("club").url("www.osss.pl").points(31).id(1).build(),
                Track.builder().title("aa2a").artist("b32bb").genre("dance").url("www.dksnc.pl").points(11).id(2).build(),
                Track.builder().title("aa34a").artist("bb34b").genre("club").url("www.kdmx.pl").points(55).id(3).build()));
        return "All tracks have been added!";
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

    @GetMapping(value = "/findByRecord/{recordId}")
    List<Track> findByRecord(@PathVariable Integer id) {
        return trackService.findByRecord(id);
    }
}
