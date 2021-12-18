package com.oskarro.muzikum.user;

import java.util.List;
import java.util.Optional;

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

    Optional<User> findUserByResetToken(String resetToken);

    User findUserByEmail(final String email);

    boolean checkIfValidOldPassword(final User user, final String oldPassword);

    void changeUserPassword(final User user, final String password);

    User updateUser(final UserDto userDto);

}
