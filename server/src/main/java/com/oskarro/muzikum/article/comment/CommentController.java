package com.oskarro.muzikum.article.comment;

import com.oskarro.muzikum.article.post.PostRepository;
import com.oskarro.muzikum.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/posts")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CommentController {

    private CommentRepository commentRepository;
    private PostRepository postRepository;

    public CommentController(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @GetMapping(value = "/{postId}/comments")
    public Page<Comment> getAllCommentsByPostId(@PathVariable Integer postId,
                                                Pageable pageable) {
        return commentRepository.findByPostId(postId, pageable);
    }

    @PostMapping(value = "/{postId}/comments")
    public Comment createComment(@PathVariable Integer postId,
                                 @Valid @RequestBody Comment comment) {
        return postRepository.findById(postId)
                .map(post -> {
                    comment.setPost(post);
                    return commentRepository.save(comment);
                }).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
    }

    @PutMapping(value = "/{postId}/comments/{commentId}")
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
    public ResponseEntity<?> deleteComment(@PathVariable Integer postId,
                                           @PathVariable Integer commentId) {
        return commentRepository.findByIdAndPostId(commentId, postId)
                .map(comment -> {
                    commentRepository.delete(comment);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
    }
}
