package com.oskarro.muzikum.track;

import com.oskarro.muzikum.model.AbstractJpaDao;
import com.oskarro.muzikum.track.model.Track;
import org.springframework.stereotype.Repository;

@Repository
public class TrackDaoImpl extends AbstractJpaDao<Track, Integer> implements TrackDao {

    public TrackDaoImpl() {
        super();
        setClazz(Track.class);
    }
}
