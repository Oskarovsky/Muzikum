package com.oskarro.muzikum.article.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest {

    private Integer id;
    private String title;
    private String description;
    private String content;
    private Integer userId;
}
