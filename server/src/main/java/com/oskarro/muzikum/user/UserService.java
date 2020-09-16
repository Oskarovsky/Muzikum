package com.oskarro.muzikum.user;

import java.util.List;

public interface UserService {

    List<User> getLastAddedUsers(Integer numberOfUsers);

    void updateUserStatistics(User user);

    void resetMonthlyStatsForUploading();

    void resetWeeklyStatsForUploading();

}
