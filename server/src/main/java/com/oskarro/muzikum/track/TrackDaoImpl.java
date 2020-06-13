package com.oskarro.muzikum.track;

import com.oskarro.muzikum.model.AbstractJpaDao;
import com.oskarro.muzikum.user.User;
import com.oskarro.muzikum.user.UserDao;
import org.springframework.stereotype.Repository;

@Repository
public class TrackDaoImpl extends AbstractJpaDao<Track, Integer> implements TrackDao {

    public TrackDaoImpl() {
        super();
        setClazz(Track.class);
    }
}
