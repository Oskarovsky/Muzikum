package com.oskarro.muzikum.voting;

public interface VotingService {

    Integer getNumberOfVotesByTrackId(Integer trackId);

    void addVoteForTrack(Integer trackId, String username);

    void voteForTrack(Vote vote);

    boolean isVotedForTrackByUser(Integer trackId, Integer userId);

}
