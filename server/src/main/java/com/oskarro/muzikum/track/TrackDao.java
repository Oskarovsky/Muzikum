package com.oskarro.muzikum.track;

import com.oskarro.muzikum.track.model.Track;

import java.util.List;

public interface TrackDao {

    Track findOne(Integer id);

    List<Track> findAll();

    void save(Track entity);

    void update(Track entity);

    void delete(Track entity);

    void deleteById(Integer entityId);
}
