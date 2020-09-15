package com.oskarro.muzikum.user;

import com.oskarro.muzikum.model.AbstractJpaDao;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
public class UserDaoImpl extends AbstractJpaDao<User, Integer> implements UserDao {

    public UserDaoImpl() {
        super();
        setClazz(User.class);
    }
}
