package com.oskarro.muzikum.voting.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
public class UserProfile {

    private Integer id;
    private String username;
    private Instant joinedAt;
    private Long pollCount;
    private Long voteCount;
}
