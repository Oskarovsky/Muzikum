package com.oskarro.muzikum.article.comment;

import com.oskarro.muzikum.article.post.PostRepository;
import com.oskarro.muzikum.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/posts")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CommentController {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    final private CommentService commentService;

    public CommentController(CommentRepository commentRepository, PostRepository postRepository,
                             CommentService commentService) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.commentService = commentService;
    }

    @GetMapping(value = "/{postId}/comments")
    @Transactional
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public Page<Comment> getAllCommentsByPostId(@PathVariable Integer postId,
                                                Pageable pageable) {
        return commentService.getAllCommentsByPostId(postId, pageable);
    }

    @GetMapping(value = "/{postId}/comments/all")
    @Transactional
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public List<Comment> getAllCommentsByPostId(@PathVariable Integer postId) {
        return commentService.getAllCommentsByPostId(postId);
    }

    @PostMapping(value = "/{postId}/comments")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public Comment createComment(@PathVariable Integer postId,
                                 @Valid @RequestBody Comment comment) {
        return postRepository.findById(postId)
                .map(post -> {
                    comment.setPost(post);
                    return commentRepository.save(comment);
                }).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
    }

    @PutMapping(value = "/{postId}/comments/{commentId}")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public Comment updateComment(@PathVariable Integer postId,
                                 @PathVariable Integer commentId,
                                 @Valid @RequestBody Comment commentRequest) {
        if (!postRepository.existsById(postId)) {
            throw new ResourceNotFoundException("Post", "id", postId);
        }

        return commentRepository.findById(commentId)
                .map(comment -> {
                    comment.setText(commentRequest.getText());
                    return commentRepository.save(comment);
                }).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
    }

    @DeleteMapping(value = "/{postId}/comments/{commentId}")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @Transactional
    public ResponseEntity<?> deleteComment(@PathVariable Integer postId,
                                           @PathVariable Integer commentId) {
        return commentRepository.findByIdAndPostId(commentId, postId)
                .map(comment -> {
                    commentRepository.delete(comment);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
    }
}