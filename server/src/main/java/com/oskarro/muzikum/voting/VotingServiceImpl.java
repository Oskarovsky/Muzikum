package com.oskarro.muzikum.voting;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VotingServiceImpl implements VotingService {

    VotingRepository votingRepository;

    public VotingServiceImpl(VotingRepository votingRepository) {
        this.votingRepository = votingRepository;
    }

    @Override
    public Integer getNumberOfVotesByTrackId(Integer trackId) {
       return votingRepository.findVotesByTrackId(trackId).size();
    }

    @Override
    public void addVoteForTrackById(Vote vote) {
        votingRepository.save(vote);
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
