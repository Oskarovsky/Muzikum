package com.oskarro.muzikum.voting;

import com.oskarro.muzikum.dto.VoteDto;
import com.oskarro.muzikum.exception.ResourceNotFoundException;
import com.oskarro.muzikum.track.model.Track;
import com.oskarro.muzikum.track.TrackService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.ValidationException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/vote")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@AllArgsConstructor
public class VotingController {

    VotingRepository votingRepository;
    TrackService trackService;
    VotingService votingService;

    @GetMapping(value = "/track/{trackId}")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<List<Vote>> getVotesByTrackId(@PathVariable Integer trackId) {
        return new ResponseEntity<>(votingRepository.findVotesByTrackId(trackId), HttpStatus.OK);
    }

    @GetMapping(value = "/track/{trackId}/all")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<Integer> getNumberOfVotesByTrackId(@PathVariable Integer trackId) {
        return new ResponseEntity<>(votingService.getNumberOfVotesByTrackId(trackId), HttpStatus.OK);
    }

    @GetMapping(value = "/track/{trackId}/add/{username}")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<Void> addVoteForTrackById(@PathVariable Integer trackId, @PathVariable String username) {
        votingService.addVoteForTrack(trackId, username);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/track")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<Void> voteForTrackById(@RequestBody VoteDto vote) throws ValidationException {
        votingService.voteForTrack(vote);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @GetMapping(value = "/{username}/tracks")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    ResponseEntity<List<Track>> getAllVotedTracksByUser(@PathVariable String username) {
        List<Vote> votesForTracks = votingRepository.findVotesByUserUsername(username);
        List<Track> tracks = new ArrayList<>();
        votesForTracks.forEach(x -> tracks.add(trackService.findById(x.getTrack().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Track", "id", x))));
        return new ResponseEntity<>(tracks, HttpStatus.OK);
    }

    @GetMapping(value = "/{username}/tracks/ids")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    ResponseEntity<List<Integer>> getAllVotedTracksIdsByUser(@PathVariable String username) {
        List<Vote> votesForTracks = votingRepository.findVotesByUserUsername(username);
        List<Track> tracks = new ArrayList<>();
        votesForTracks.forEach(x -> tracks.add(trackService.findById(x.getTrack().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Track", "id", x))));
        List<Integer> tracksIds = new ArrayList<>();
        tracks.forEach(x -> tracksIds.add(x.getId()));
        return new ResponseEntity<>(tracksIds, HttpStatus.OK);
    }
}
