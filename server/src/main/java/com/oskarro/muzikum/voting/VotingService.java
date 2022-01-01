package com.oskarro.muzikum.voting;

import java.util.List;

public interface VotingService {

    List<Vote> getVotesByTrackId(final Integer trackId);

    List<Vote> getVotesByUserUsername(final String username);

    Integer getNumberOfVotesByTrackId(Integer trackId);

    void addVoteForTrack(Integer trackId, String username);

    void voteForTrack(VoteDto vote);

    boolean isVotedForTrackByUser(Integer trackId, Integer userId);

}
