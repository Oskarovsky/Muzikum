package com.oskarro.muzikum.article.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    List<Post> findByUserId(Integer id);

    List<Post> findByUserUsername(String username);

    List<Post> findAllByOrderByCreatedAtDesc();

}
