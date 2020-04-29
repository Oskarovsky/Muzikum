package com.oskarro.muzikum.voting.payload.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChoiceResponse {

    private Integer id;
    private String text;
    private long voteCount;
}
