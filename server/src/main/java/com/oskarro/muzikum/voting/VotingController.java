package com.oskarro.muzikum.voting;

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
@CrossOrigin
@AllArgsConstructor
public class VotingController {

    VotingRepository votingRepository;
    TrackService trackService;
    VotingService votingService;

    @GetMapping(value = "/track/{trackId}")
    public ResponseEntity<List<Vote>> getVotesByTrackId(@PathVariable Integer trackId) {
        return new ResponseEntity<>(votingRepository.findVotesByTrackId(trackId), HttpStatus.OK);
    }

    @GetMapping(value = "/track/{trackId}/all")
    public ResponseEntity<Integer> getNumberOfVotesByTrackId(@PathVariable Integer trackId) {
        return new ResponseEntity<>(votingService.getNumberOfVotesByTrackId(trackId), HttpStatus.OK);
    }

    @GetMapping(value = "/track/{trackId}/add/{username}")
    public ResponseEntity<Void> addVoteForTrackById(@PathVariable Integer trackId, @PathVariable String username) {
        votingService.addVoteForTrack(trackId, username);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/track")
    public ResponseEntity<Void> voteForTrackById(@RequestBody VoteDto vote) {
        votingService.voteForTrack(vote);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @GetMapping(value = "/{username}/tracks")
    ResponseEntity<List<Track>> getAllVotedTracksByUser(@PathVariable String username) {
        List<Vote> votesForTracks = votingRepository.findVotesByUserUsername(username);
        List<Track> tracks = new ArrayList<>();
        votesForTracks.forEach(x -> tracks.add(trackService.findById(x.getTrack().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Track", "id", x))));
        return new ResponseEntity<>(tracks, HttpStatus.OK);
    }

    @GetMapping(value = "/{username}/tracks/ids")
    ResponseEntity<List<Integer>> getAllVotedTracksIdsByUser(@PathVariable String username) {
        List<Vote> votesForTracks = votingRepository.findVotesByUserUsername(username);
        List<Track> tracks = new ArrayList<>();
        votesForTracks.forEach(x -> tracks.add(trackService.findById(x.getTrack().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Track", "id", x))));
        List<Integer> tracksIds = new ArrayList<>();
        tracks.forEach(x -> tracksIds.add(x.getId()));
        return new ResponseEntity<>(tracksIds, HttpStatus.OK);
    }

    @GetMapping(value = "/track/{trackId}/checkVote/{userId}")
    ResponseEntity<Boolean> checkIfUserVotedForTrack(@PathVariable Integer trackId, @PathVariable Integer userId) {
        if (votingService.isVotedForTrackByUser(trackId, userId)) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false, HttpStatus.OK);
        }
    }
}
