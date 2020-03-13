package com.oskarro.muzikum.track;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface TrackRepository extends CrudRepository<Track, Integer> {
}
