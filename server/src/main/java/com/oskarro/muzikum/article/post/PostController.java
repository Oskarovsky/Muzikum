package com.oskarro.muzikum.article.post;

import com.oskarro.muzikum.exception.ResourceNotFoundException;
import com.oskarro.muzikum.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/posts")
@CrossOrigin
public class PostController {

    private final PostRepository postRepository;
    private final PostService postService;

    public PostController(PostRepository postRepository, PostService postService) {
        this.postRepository = postRepository;
        this.postService = postService;
    }

    @GetMapping
    public Page<Post> getAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    @GetMapping(value = "/{postId}")
    public Optional<Post> getPostById(@PathVariable Integer postId) {
        return postService.getPostById(postId);
    }

    @GetMapping(value = "/user/{username}")
    @Transactional
    public List<Post> getPostsByUsername(@PathVariable String username) {
        return postService.findByUsername(username);
    }

    @GetMapping(value = "/userId/{userId}")
    @Transactional
    public List<Post> getPostsByUserId(@PathVariable Integer userId) {
        return postService.findByUserId(userId);
    }

    @PostMapping
    public Post createPost(@Valid @RequestBody Post post) {
        return postService.createPost(post);
    }

    @PutMapping(value = "/{postId}")
    @Transactional
    public Post updatePost(@PathVariable Integer postId,
                           @Valid @RequestBody Post postRequest) {
        return postRepository.findById(postId)
                .map(post -> {
                    post.setTitle(postRequest.getTitle());
                    post.setDescription(postRequest.getDescription());
                    post.setContent(postRequest.getContent());
                    return postRepository.save(post);
                }).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
    }

    @DeleteMapping(value = "/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Integer postId) {
        return postRepository.findById(postId)
                .map(post -> {
                    postRepository.delete(post);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
    }

    @GetMapping(value = "/lastAdded/{quantity}")
    @Transactional
    public List<Post> getLastAddedPosts(@PathVariable Integer quantity) {
        return postService.getLastAddedPosts(quantity);
    }
}
