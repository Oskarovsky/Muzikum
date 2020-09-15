package com.oskarro.muzikum.user;

import com.oskarro.muzikum.track.model.Track;

import java.util.List;

public interface UserService {

    List<User> getLastAddedUsers(Integer numberOfUsers);

    List<Track> getTopUploader();

}
