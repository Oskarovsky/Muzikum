package com.oskarro.muzikum.user;

import java.util.List;

public interface UserService {

    List<User> getLastAddedUsers(Integer numberOfUsers);

    void updateUserStatistics(User user);

    void resetMonthlyStatsForUploading();

    void resetWeeklyStatsForUploading();

    User getTopUploader(String periodOfTime);

    List<User> getTopUploaders(String periodOfTime, int numberOfUser);

}
