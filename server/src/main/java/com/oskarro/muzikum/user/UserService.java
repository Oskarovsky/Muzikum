package com.oskarro.muzikum.user;

import com.oskarro.muzikum.model.GenericDao;
import com.oskarro.muzikum.model.GenericDaoImpl;

import java.util.List;
import java.util.Optional;

public interface UserService extends GenericDao<User, Integer>{

    List<User> getLastAddedUsers(Integer numberOfUsers);

    List<User> getAllUsers();

}
