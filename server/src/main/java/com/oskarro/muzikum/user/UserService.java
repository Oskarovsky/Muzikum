package com.oskarro.muzikum.user;

import io.jsonwebtoken.Jwts;

import java.util.List;

public interface UserService {

    List<User> getLastAddedUsers(Integer numberOfUsers);

    void updateUserStatistics(User user);

    void resetMonthlyStatsForUploading();

    void resetWeeklyStatsForUploading();

    User getTopUploader(String periodOfTime);

    List<User> getTopUploaders(String periodOfTime, int numberOfUser);

    Integer getNumberOfTracksAddedByUserId(Integer userId);

    Integer getNumberOfTracksAddedByUsername(String username);

    Integer getNumberOfTracksAddedInGivenPeriodByUserId(Integer userId, String periodOfTime);

    Integer getNumberOfTracksAddedInGivenPeriodByUsername(String username, String periodOfTime);

    User getUserFromToken(String token);

}
