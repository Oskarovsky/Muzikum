package com.oskarro.muzikum.user.favorite;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteTrackRepository extends JpaRepository<FavoriteTrack, Integer> {

    List<FavoriteTrack> findAll();

    Optional<FavoriteTrack> findById(Integer id);

    FavoriteTrack save(FavoriteTrack FavoriteTrack);

    List<FavoriteTrack> findFavoriteTracksByUserId(Integer userId);

    List<FavoriteTrack> findFavoriteTracksByTrackId(Integer trackId);

    List<FavoriteTrack> findFavoriteTracksByUserUsername(String username);

    Optional<FavoriteTrack> findFavoriteTrackByTrackIdAndUserUsername(Integer trackId, String username);

    void deleteById(Integer id);
}
