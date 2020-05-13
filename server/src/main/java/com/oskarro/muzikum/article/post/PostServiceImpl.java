package com.oskarro.muzikum.article.post;

import com.oskarro.muzikum.exception.ResourceNotFoundException;
import com.oskarro.muzikum.user.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public void createPost(Post post) {
        postRepository.save(post);
    }

    @Override
    public List<Post> findByUsername(String username) {
        return postRepository.findByUserUsername(username);
    }

    @Override
    public List<Post> findByUserId(Integer userId) {
        return postRepository.findByUserId(userId);
    }

    @Override
    public Optional<Post> getPostById(Integer postId) {
        return postRepository.findById(postId);
    }
}
