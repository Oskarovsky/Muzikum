package com.oskarro.muzikum.user;

import java.util.List;

public interface UserService {

    List<User> getLastAddedUsers(Integer numberOfUsers);

}
