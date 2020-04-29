package com.oskarro.muzikum.voting.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserSummary {

    private Integer id;
    private String username;
    private String name;
}
