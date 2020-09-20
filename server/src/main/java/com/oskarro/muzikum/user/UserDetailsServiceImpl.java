package com.oskarro.muzikum.user;

import com.oskarro.muzikum.exception.ResourceNotFoundException;
import com.oskarro.muzikum.track.TrackRepository;
import com.oskarro.muzikum.track.model.Track;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService, UserService {

    private final static String WEEKLY_PERIOD = "week";
    private final static String MONTHLY_PERIOD = "month";
    private final static String TOTAL_PERIOD = "total";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDaoImpl dao;

    @Autowired
    private TrackRepository trackRepository;

    @Autowired
    private UserStatisticsRepository userStatisticsRepository;

    public UserDetailsServiceImpl() {
        super();
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username or email:" + usernameOrEmail));
        return UserPrincipal.create(user);
    }

    @Transactional
    public UserDetails loadUserById(Integer id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("User not found with id: " + id)
        );
        return UserPrincipal.create(user);
    }


    @Override
    public List<User> getLastAddedUsers(Integer numberOfUsers) {
        List<User> fetchedUsers = userRepository.findAllByOrderByCreatedAtDesc();
        return fetchedUsers
                .stream()
                .limit(numberOfUsers)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }


    @Override
    public void updateUserStatistics(User user) {
        UserStatistics userStatistics = userStatisticsRepository.findByUserId(user.getId()).orElseThrow(null);
        if (userStatistics.getMonthUpload() == null) {
            userStatistics.setMonthUpload(1);
        } else {
            userStatistics.setMonthUpload(userStatistics.getMonthUpload() + 1);
        }
        if (userStatistics.getWeekUpload() == null) {
            userStatistics.setWeekUpload(1);
        } else {
            userStatistics.setWeekUpload(userStatistics.getWeekUpload() + 1);
        }
        if (userStatistics.getTotalUpload() == null) {
            userStatistics.setTotalUpload(1);
        } else {
            userStatistics.setTotalUpload(userStatistics.getTotalUpload() + 1);
        }
    }


    @Override
    public void resetMonthlyStatsForUploading() {
        List<UserStatistics> userStatistics = userStatisticsRepository.findAll();
        for (UserStatistics stats : userStatistics) {
            stats.setMonthUpload(0);
            userStatisticsRepository.save(stats);
        }
    }


    @Override
    public void resetWeeklyStatsForUploading() {
        List<UserStatistics> userStatistics = userStatisticsRepository.findAll();
        for (UserStatistics stats : userStatistics) {
            stats.setWeekUpload(0);
            userStatisticsRepository.save(stats);
        }
    }

    @Override
    public User getTopUploader(String periodOfTime) {
        switch (periodOfTime) {
            case TOTAL_PERIOD: {
                List<User> userList = userStatisticsRepository.findTotalTopUploader();
                return userList.stream().findFirst().orElse(null);
            }
            case MONTHLY_PERIOD: {
                List<User> userList = userStatisticsRepository.findMonthlyTopUploader();
                return userList.stream().findFirst().orElse(null);
            }
            case WEEKLY_PERIOD: {
                List<User> userList = userStatisticsRepository.findWeeklyTopUploader();
                return userList.stream().findFirst().orElse(null);
            }
            default:
                throw new RuntimeException("Period of time hasn't been declared");
        }
    }


    @Override
    public List<User> getTopUploaders(String periodOfTime, int numberOfUser) {
        switch (periodOfTime) {
            case TOTAL_PERIOD: {
                List<User> userList = userStatisticsRepository.findTotalTopUploaders();
                return userList.stream().limit(numberOfUser).collect(Collectors.toList());
            }
            case MONTHLY_PERIOD: {
                List<User> userList = userStatisticsRepository.findMonthlyTopUploaders();
                return userList.stream().limit(numberOfUser).collect(Collectors.toList());
            }
            case WEEKLY_PERIOD: {
                List<User> userList = userStatisticsRepository.findWeeklyTopUploaders();
                return userList.stream().limit(numberOfUser).collect(Collectors.toList());
            }
            default:
                throw new RuntimeException("Period of time hasn't been declared");
        }
    }

    @Override
    public Integer getNumberOfTracksAddedByUserId(Integer userId) {
        return userStatisticsRepository.getNumberOfTracksAddedByUserId(userId);
    }

    @Override
    public Integer getNumberOfTracksAddedByUsername(String username) {
        return userStatisticsRepository.getNumberOfTracksAddedByUsername(username);
    }

    @Override
    public Integer getNumberOfTracksAddedInGivenPeriodByUserId(Integer userId, String periodOfTime) {
        switch (periodOfTime) {
            case TOTAL_PERIOD: {
                return userStatisticsRepository.findByUserId(userId)
                        .orElseThrow(() -> new ResourceNotFoundException("User stats", "userId", userId))
                        .getTotalUpload();
            }
            case MONTHLY_PERIOD: {
                return userStatisticsRepository.findByUserId(userId)
                        .orElseThrow(() -> new ResourceNotFoundException("User stats", "userId", userId))
                        .getMonthUpload();
            }
            case WEEKLY_PERIOD: {
                return userStatisticsRepository.findByUserId(userId)
                        .orElseThrow(() -> new ResourceNotFoundException("User stats", "userId", userId))
                        .getWeekUpload();
            }
            default:
                throw new RuntimeException("User stats for user with id " + userId + " not found");
        }
    }

    @Override
    public Integer getNumberOfTracksAddedInGivenPeriodByUsername(String username, String periodOfTime) {
        switch (periodOfTime) {
            case TOTAL_PERIOD: {
                return userStatisticsRepository.findByUserUsername(username)
                        .orElseThrow(() -> new ResourceNotFoundException("User stats", "userId", username))
                        .getTotalUpload();
            }
            case MONTHLY_PERIOD: {
                return userStatisticsRepository.findByUserUsername(username)
                        .orElseThrow(() -> new ResourceNotFoundException("User stats", "userId", username))
                        .getMonthUpload();
            }
            case WEEKLY_PERIOD: {
                return userStatisticsRepository.findByUserUsername(username)
                        .orElseThrow(() -> new ResourceNotFoundException("User stats", "userId", username))
                        .getWeekUpload();
            }
            default:
                throw new RuntimeException("User stats for user with username " + username + " not found");
        }
    }
}
