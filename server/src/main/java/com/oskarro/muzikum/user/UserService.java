package com.oskarro.muzikum.user;

import com.oskarro.muzikum.model.GenericService;

import java.util.List;

public interface UserService extends GenericService<User, Integer> {

    List<User> getLastAddedUsers(Integer numberOfUsers);

    List<User> getAllUsers();

}
