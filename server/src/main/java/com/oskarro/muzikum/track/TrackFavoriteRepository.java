package com.oskarro.muzikum.track;


import com.oskarro.muzikum.track.model.TrackFavorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrackFavoriteRepository extends JpaRepository<TrackFavorite, Integer> {

    List<TrackFavorite> findAll();

    Optional<TrackFavorite> findById(Integer id);

    TrackFavorite save(TrackFavorite TrackFavorite);

    List<TrackFavorite> findFavoriteTracksByUserUsername(String username);

    Optional<TrackFavorite> findFavoriteTrackByTrackIdAndUserUsername(Integer trackId, String username);

    void deleteById(Integer id);
}
