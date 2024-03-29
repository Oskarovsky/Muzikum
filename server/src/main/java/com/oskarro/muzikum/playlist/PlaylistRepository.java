package com.oskarro.muzikum.playlist;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.Optional;

@Repository
@CrossOrigin
public interface PlaylistRepository extends CrudRepository<Playlist, Integer> {

    List<Playlist> findAll();

    Optional<Playlist> findById(Integer id);

    Optional<Playlist> findByName(String name);

    Playlist save(Playlist playlist);

    void deleteById(Integer id);

    List<Playlist> findAllByUserUsername(String username);

    List<Playlist> findAllByOrderByCreatedAtDesc();

}
