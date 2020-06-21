package com.oskarro.muzikum.voting;

import com.oskarro.muzikum.exception.ResourceNotFoundException;
import com.oskarro.muzikum.track.Track;
import com.oskarro.muzikum.track.TrackService;
import com.oskarro.muzikum.user.User;
import com.oskarro.muzikum.user.favorite.FavoriteTrack;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.bind.ValidationException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/vote")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class VotingController {

    VotingRepository votingRepository;
    TrackService trackService;
    VotingService votingService;

    public VotingController(VotingRepository votingRepository, TrackService trackService, VotingService votingService) {
        this.votingRepository = votingRepository;
        this.trackService = trackService;
        this.votingService = votingService;
    }

    @GetMapping(value = "/track/{trackId}")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public List<Vote> getVotesByTrackId(@PathVariable Integer trackId) {
        return votingRepository.findVotesByTrackId(trackId);
    }

    @GetMapping(value = "/track/{trackId}/all")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public Integer getNumberOfVotesByTrackId(@PathVariable Integer trackId) {
        return votingService.getNumberOfVotesByTrackId(trackId);
    }

    @GetMapping(value = "/track/{trackId}/add/{username}")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public void addVoteForTrackById(@PathVariable Integer trackId, @PathVariable String username) throws ValidationException {
        votingService.addVoteForTrack(trackId, username);
    }

    @GetMapping(value = "/{username}/tracks")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    List<Track> getAllVotedTracksByUser(@PathVariable String username) {
        List<Vote> votesForTracks = votingRepository.findVotesByUserUsername(username);
        List<Track> tracks = new ArrayList<>();
        votesForTracks.forEach(x -> tracks.add(trackService.findById(x.getTrack().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Track", "id", x))));
        return tracks;
    }

    @GetMapping(value = "/{username}/tracks/ids")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    List<Integer> getAllVotedTracksIdsByUser(@PathVariable String username) {
        List<Vote> votesForTracks = votingRepository.findVotesByUserUsername(username);
        List<Track> tracks = new ArrayList<>();
        votesForTracks.forEach(x -> tracks.add(trackService.findById(x.getTrack().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Track", "id", x))));
        List<Integer> tracksIds = new ArrayList<>();
        tracks.forEach(x -> tracksIds.add(x.getId()));
        return tracksIds;
    }
}
