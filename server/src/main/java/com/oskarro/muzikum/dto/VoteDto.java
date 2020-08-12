package com.oskarro.muzikum.dto;

import com.oskarro.muzikum.voting.VoteType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteDto {

    private VoteType voteType;
    private Integer trackId;
}
