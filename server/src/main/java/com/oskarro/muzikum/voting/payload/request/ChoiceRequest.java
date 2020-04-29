package com.oskarro.muzikum.voting.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class ChoiceRequest {

    @NotBlank
    @Size(max = 255)
    private String text;
}
