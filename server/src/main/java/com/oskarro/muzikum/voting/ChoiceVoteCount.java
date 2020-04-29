package com.oskarro.muzikum.voting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChoiceVoteCount {

    private Integer choiceId;
    private Long voteCount;

}
