package com.oskarro.muzikum.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    private Integer id;
    private String text;
    private Integer postId;
    private Integer userId;
}
