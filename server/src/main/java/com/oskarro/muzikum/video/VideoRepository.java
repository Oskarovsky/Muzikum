package com.oskarro.muzikum.video;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VideoRepository extends JpaRepository<Video, Integer> {

    List<Video> findAll();

    List<Video> findVideosByCategory(String category);

    Optional<Video> findById(Integer id);

    Video save(Video video);

    void deleteById(Integer id);
}
