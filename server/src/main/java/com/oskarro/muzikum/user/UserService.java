package com.oskarro.muzikum.user;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> getLastAddedUsers(Integer numberOfUsers);

    List<User> getAllUsers();

}
