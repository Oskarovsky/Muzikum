package com.oskarro.muzikum.article.post;

import java.util.List;
import java.util.Optional;

public interface PostService {

    List<Post> getAllPosts();

    Post createPost(Post post);

    List<Post> findByUsername(String username);

    List<Post> findByUserId(Integer userId);

    Optional<Post> getPostById(Integer postId);

    List<Post> getLastAddedPosts(Integer numberOfPosts);

}
