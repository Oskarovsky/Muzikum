package com.oskarro.muzikum.article.comment;

import com.oskarro.muzikum.article.post.PostRepository;
import com.oskarro.muzikum.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/posts")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@AllArgsConstructor
public class CommentController {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final CommentService commentService;

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
    public ResponseEntity<List<Comment>> getAllCommentsByPostId(@PathVariable Integer postId) {
        return new ResponseEntity<>(commentService.getAllCommentsByPostId(postId), HttpStatus.OK);
    }

    @PostMapping(value = "/{postId}/comments")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<Comment> createComment(@PathVariable Integer postId,
                                                 @Valid @RequestBody Comment comment) {
        return postRepository.findById(postId)
                .map(post -> {
                    comment.setPost(post);
                    return new ResponseEntity<>(commentRepository.save(comment), HttpStatus.OK);
                }).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
    }

    @PutMapping(value = "/{postId}/comments/{commentId}")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<Comment> updateComment(@PathVariable Integer postId,
                                 @PathVariable Integer commentId,
                                 @Valid @RequestBody Comment commentRequest) {
        if (!postRepository.existsById(postId)) {
            throw new ResourceNotFoundException("Post", "id", postId);
        }
        return commentRepository.findById(commentId)
                .map(com -> {
                    com.setText(commentRequest.getText());
                    return new ResponseEntity<>(commentRepository.save(com), HttpStatus.OK);
                }).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
    }

    @DeleteMapping(value = "/{postId}/comments/{commentId}")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @Transactional
    public ResponseEntity<Integer> deleteComment(@PathVariable Integer postId,
                                                 @PathVariable Integer commentId) {
        return commentRepository.findByIdAndPostId(commentId, postId)
                .map(comment -> {
                    commentRepository.delete(comment);
                    return new ResponseEntity<>(commentId, HttpStatus.OK);
                }).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
    }
}
