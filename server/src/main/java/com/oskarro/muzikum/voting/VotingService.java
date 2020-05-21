package com.oskarro.muzikum.voting;

public interface VotingService {

    Integer getNumberOfVotesByTrackId(Integer trackId);

    void addVoteForTrackById(Vote vote);

    boolean isVotedForTrackByUser(Integer trackId, Integer userId);

}
