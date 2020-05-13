package com.oskarro.muzikum.article.comment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentService {

    List<Comment> getAllCommentsByPostId(Integer postId);

    Page<Comment> getAllCommentsByPostId(Integer postId, Pageable pageable);

}
