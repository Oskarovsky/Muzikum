package com.oskarro.muzikum.voting;

import com.oskarro.muzikum.exception.AppException;
import com.oskarro.muzikum.exception.ResourceNotFoundException;
import com.oskarro.muzikum.track.Track;
import com.oskarro.muzikum.track.TrackRepository;
import com.oskarro.muzikum.user.User;
import com.oskarro.muzikum.user.UserRepository;
import com.oskarro.muzikum.user.favorite.FavoriteTrack;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.oskarro.muzikum.voting.VoteType.DOWNVOTE;
import static com.oskarro.muzikum.voting.VoteType.UPVOTE;

@Slf4j
@Service
@AllArgsConstructor
public class VotingServiceImpl implements VotingService {

    private final VotingRepository votingRepository;
    private final UserRepository userRepository;
    private final TrackRepository trackRepository;

    @Override
    public Integer getNumberOfVotesByTrackId(Integer trackId) {
       return votingRepository.findVotesByTrackId(trackId).size();
    }

    @Override
    public void addVoteForTrack(Integer trackId, String username) {
        Optional<Vote> voteDemo =
                votingRepository.findVotesByTrackIdAndUserUsername(trackId, username);
        if (voteDemo.isPresent()) {
            log.warn("You can add vote for track only once");
        } else {
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
            Track track = trackRepository.findById(trackId)
                    .orElseThrow(() -> new ResourceNotFoundException("Track", "trackId", trackId));
            Vote vote = Vote.builder().track(track).user(user).build();
            if (track.getPoints() == null) {
                track.setPoints(1);
            } else {
                track.setPoints(track.getPoints() + 1);
            }
            user.setFavoriteTrack(track);
            trackRepository.save(track);
            votingRepository.save(vote);
        }
    }

    @Override
    public void voteForTrack(Vote vote) {
        Track track = trackRepository.findById(vote.getTrack().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Track", "id", vote.getTrack().getId()));
        if (isVotedForTrackByUser(track.getId(), vote.getUser().getId())) {
            throw new AppException("You have already voted for this track");
        }
        addVote(track, vote.getVoteType());
        votingRepository.save(vote);
        trackRepository.save(track);
    }

    void addVote(Track track, VoteType voteType) {
        if (UPVOTE.equals(voteType)) {
            if (track.getPoints() == null) {
                track.setPoints(1);
            } else {
                track.setPoints(track.getPoints() + 1);
            }
        } else if (DOWNVOTE.equals(voteType)) {
            if (track.getPoints() == null) {
                track.setPoints(-1);
            } else {
                track.setPoints(track.getPoints() - 1);
            }
        }
    }

    @Override
    public boolean isVotedForTrackByUser(Integer trackId, Integer userId) {
        List<Vote> votes = votingRepository.findVotesByTrackId(trackId);
        Vote vote = votes.stream()
                .filter(v -> v.getUser().getId().equals(userId))
                .findAny()
                .orElse(null);
        return Optional.ofNullable(vote).isPresent();
    }
}
