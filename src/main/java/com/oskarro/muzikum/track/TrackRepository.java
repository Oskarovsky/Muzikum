package com.oskarro.muzikum.track;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface TrackRepository extends CrudRepository<Track, Integer> {

    List<Track> findAll();

    Optional<Track> findById(Integer id);

    Track save(Track track);

    List<Track> findTracksByProviderId(Integer id);

}
