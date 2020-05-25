package com.oskarro.muzikum.voting;

import com.oskarro.muzikum.exception.ResourceNotFoundException;
import com.oskarro.muzikum.track.Track;
import com.oskarro.muzikum.track.TrackService;
import com.oskarro.muzikum.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.bind.ValidationException;
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

    @PostMapping(value = "/track/{trackId}/add")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public void addVoteForTrackById(@PathVariable Integer trackId, @Valid @RequestBody Vote voteRequest) throws ValidationException {
        Track track = trackService.findById(trackId).orElseThrow(() -> new ResourceNotFoundException("Track", "id", trackId));
        if (!votingService.isVotedForTrackByUser(trackId, voteRequest.getUser().getId())) {
            Vote vote = Vote.builder().track(track).user(voteRequest.getUser()).build();
            votingRepository.save(vote);
            log.info("Vote has been added");
        } else {
            throw new RuntimeException("Fail! -> Cause: User can vote for track only once");
        }
    }
}
