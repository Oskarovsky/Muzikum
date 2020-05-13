package com.oskarro.muzikum.article.post;

import com.oskarro.muzikum.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/posts")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PostController {

    private PostRepository postRepository;
    private PostService postService;

    public PostController(PostRepository postRepository, PostService postService) {
        this.postRepository = postRepository;
        this.postService = postService;
    }

    @GetMapping(value = "/all")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public Page<Post> getAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    @GetMapping(value = "/user/{username}")
    public List<Post> getPostsByUsername(@PathVariable String username) {
        return postService.findByUsername(username);
    }

    @GetMapping(value = "/userId/{userId}")
    public List<Post> getPostsByUserId(@PathVariable Integer userId) {
        return postService.findByUserId(userId);
    }

    @PostMapping(value = "/new")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public void createPost(@Valid @RequestBody Post post) {
        postService.createPost(post);
    }

    @PutMapping(value = "/{postId}")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public Post updatePost(@PathVariable Integer postId, @Valid @RequestBody Post postRequest) {
        return postRepository.findById(postId)
                .map(post -> {
                    post.setTitle(postRequest.getTitle());
                    post.setDescription(postRequest.getDescription());
                    post.setContent(postRequest.getContent());
                    return postRepository.save(post);
                }).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
    }

    @DeleteMapping(value = "/{postId}")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<?> deletePost(@PathVariable Integer postId) {
        return postRepository.findById(postId)
                .map(post -> {
                    postRepository.delete(post);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
    }
}
