package com.oskarro.muzikum.voting;

public enum VoteType {

    UPVOTE(1), DOWNVOTE(-1),
    ;

    VoteType(int direction) { }
}
