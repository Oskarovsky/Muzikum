package com.oskarro.muzikum.voting;

import com.oskarro.muzikum.track.TrackService;
import com.oskarro.muzikum.track.model.Track;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/vote")
@CrossOrigin
public class VotingController {

    private final TrackService trackService;
    private final VotingService votingService;

    public VotingController(final TrackService trackService,
                            final VotingService votingService) {
        this.trackService = trackService;
        this.votingService = votingService;
    }

    @GetMapping(value = "/track/{trackId}")
    public ResponseEntity<List<Vote>> getVotesByTrackId(@PathVariable final Integer trackId) {
        return new ResponseEntity<>(votingService.getVotesByTrackId(trackId), HttpStatus.OK);
    }

    @GetMapping(value = "/track/{trackId}/count")
    public ResponseEntity<Integer> getNumberOfVotesByTrackId(@PathVariable final Integer trackId) {
        return new ResponseEntity<>(votingService.getNumberOfVotesByTrackId(trackId), HttpStatus.OK);
    }

    @GetMapping(value = "/track/{trackId}/add/{username}")
    public ResponseEntity<Void> addVoteForTrackById(@PathVariable final Integer trackId,
                                                    @PathVariable final String username) {
        votingService.addVoteForTrack(trackId, username);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/track")
    public ResponseEntity<Void> voteForTrackById(@RequestBody final VoteDto vote) {
        votingService.voteForTrack(vote);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/{username}/tracks")
    ResponseEntity<List<Track>> getAllVotedTracksByUser(@PathVariable final String username) {
        return new ResponseEntity<>(
                votingService
                        .getVotesByUserUsername(username)
                        .stream()
                        .map(t -> trackService.findById(t.getTrack().getId()))
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @GetMapping(value = "/{username}/tracks/ids")
    ResponseEntity<List<Integer>> getAllVotedTracksIdsByUser(@PathVariable final String username) {
        return new ResponseEntity<>(
                votingService
                        .getVotesByUserUsername(username)
                        .stream()
                        .map(t -> trackService.findById(t.getTrack().getId()))
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .map(Track::getId)
                        .collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @GetMapping(value = "/track/{trackId}/checkVote/{userId}")
    ResponseEntity<Boolean> checkIfUserVotedForTrack(@PathVariable final Integer trackId,
                                                     @PathVariable final Integer userId) {
        return votingService
                .isVotedForTrackByUser(trackId, userId)
                ? new ResponseEntity<>(true, HttpStatus.OK)
                : new ResponseEntity<>(false, HttpStatus.OK);
    }
}
