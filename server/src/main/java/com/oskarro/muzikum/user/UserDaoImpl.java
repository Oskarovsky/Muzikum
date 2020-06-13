package com.oskarro.muzikum.user;

import com.oskarro.muzikum.model.AbstractJpaDao;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl extends AbstractJpaDao<User, Integer> implements UserDao {

    public UserDaoImpl() {
        super();
        setClazz(User.class);
    }
}
