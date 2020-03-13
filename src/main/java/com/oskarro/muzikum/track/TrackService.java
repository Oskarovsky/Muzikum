package com.oskarro.muzikum.track;

import java.util.List;
import java.util.Optional;

public interface TrackService {

    List<Track> findAll();

    Optional<Track> findById(Integer id);

    Track saveTrack(Track track);
}
