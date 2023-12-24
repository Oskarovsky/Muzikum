package com.oskarro.muzikum.article.comment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }
    
    public List<Comment> getAllCommentsByPostId(Integer postId){
        return commentRepository.findByPostId(postId);
    }

    public Page<Comment> getAllCommentsByPostId(Integer postId, Pageable pageable) {
        return commentRepository.findByPostId(postId, pageable);
    }
}
