package com.oskarro.muzikum.video;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VideoRepository extends JpaRepository<Video, Integer> {

    List<Video> findAll();

    List<Video> findVideosByCategory(String category);

    Optional<Video> findById(Integer id);

    Video save(Video video);

    void deleteById(Integer id);

    Optional<Video> findByUrl(String url);

    List<Video> findTop10ByOrderByViewCountDesc();

    List<Video> findAllByOrderByViewCountDesc();
}
