package com.oskarro.muzikum.user;

import com.oskarro.muzikum.exception.ResourceNotFoundException;
import com.oskarro.muzikum.security.jwt.JwtTokenProvider;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService, UserService {

    private static final String WEEKLY_PERIOD = "week";
    private static final String MONTHLY_PERIOD = "month";
    private static final String TOTAL_PERIOD = "total";

    private final UserRepository userRepository;
    private final UserStatisticsRepository userStatisticsRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;


    public UserDetailsServiceImpl(UserRepository userRepository,
                                  UserStatisticsRepository userStatisticsRepository,
                                  @Lazy PasswordEncoder passwordEncoder,
                                  JwtTokenProvider tokenProvider) {
        super();
        this.userRepository = userRepository;
        this.userStatisticsRepository = userStatisticsRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(final Integer userId) {
        return userRepository
                .findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
    }

    @Override
    public User getUserByUsername(final String username) {
        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        User user = userRepository
                .findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username or email:" + usernameOrEmail));
        return UserPrincipal.create(user);
    }

    @Override
    @Transactional(readOnly = true)
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
        UserStatistics userStatistics = userStatisticsRepository
                .findByUserId(user.getId())
                .orElseThrow(null);

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
        userStatisticsRepository
                .findAll()
                .forEach(t -> {
                    t.setMonthUpload(0);
                    userStatisticsRepository.save(t);
                });
    }


    @Override
    public void resetWeeklyStatsForUploading() {
        userStatisticsRepository
                .findAll()
                .forEach(t -> {
                    t.setWeekUpload(0);
                    userStatisticsRepository.save(t);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public User getTopUploader(String periodOfTime) {
        switch (periodOfTime) {
            case TOTAL_PERIOD -> {
                List<User> userList = userStatisticsRepository.findTotalTopUploader();
                return userList.stream().findFirst().orElse(null);
            }
            case MONTHLY_PERIOD -> {
                List<User> userList = userStatisticsRepository.findMonthlyTopUploader();
                return userList.stream().findFirst().orElse(null);
            }
            case WEEKLY_PERIOD -> {
                List<User> userList = userStatisticsRepository.findWeeklyTopUploader();
                return userList.stream().findFirst().orElse(null);
            }
            default -> throw new RuntimeException("Period of time hasn't been declared");
        }
    }


    @Override
    @Transactional(readOnly = true)
    public List<User> getTopUploaders(String periodOfTime, int numberOfUser) {
        switch (periodOfTime) {
            case TOTAL_PERIOD -> {
                List<User> userList = userStatisticsRepository.findTotalTopUploaders();
                return userList.stream().limit(numberOfUser).toList();
            }
            case MONTHLY_PERIOD -> {
                List<User> userList = userStatisticsRepository.findMonthlyTopUploaders();
                return userList.stream().limit(numberOfUser).toList();
            }
            case WEEKLY_PERIOD -> {
                List<User> userList = userStatisticsRepository.findWeeklyTopUploaders();
                return userList.stream().limit(numberOfUser).toList();
            }
            default -> throw new RuntimeException("Period of time hasn't been declared");
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
            case TOTAL_PERIOD -> {
                return userStatisticsRepository.findByUserId(userId)
                        .orElseThrow(() -> new ResourceNotFoundException("Total user stats", "userId", userId))
                        .getTotalUpload();
            }
            case MONTHLY_PERIOD -> {
                return userStatisticsRepository.findByUserId(userId)
                        .orElseThrow(() -> new ResourceNotFoundException("Monthly user stats", "userId", userId))
                        .getMonthUpload();
            }
            case WEEKLY_PERIOD -> {
                return userStatisticsRepository.findByUserId(userId)
                        .orElseThrow(() -> new ResourceNotFoundException("Weekly user stats", "userId", userId))
                        .getWeekUpload();
            }
            default -> throw new RuntimeException("User stats for user with id " + userId + " not found");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Integer getNumberOfTracksAddedInGivenPeriodByUsername(String username, String periodOfTime) {
        switch (periodOfTime) {
            case TOTAL_PERIOD -> {
                return userStatisticsRepository.findByUserUsername(username)
                        .orElseThrow(() -> new ResourceNotFoundException("Total user stats", "username", username))
                        .getTotalUpload();
            }
            case MONTHLY_PERIOD -> {
                return userStatisticsRepository.findByUserUsername(username)
                        .orElseThrow(() -> new ResourceNotFoundException("Monthly user stats", "username", username))
                        .getMonthUpload();
            }
            case WEEKLY_PERIOD -> {
                return userStatisticsRepository.findByUserUsername(username)
                        .orElseThrow(() -> new ResourceNotFoundException("Weekly user stats", "username", username))
                        .getWeekUpload();
            }
            default -> throw new RuntimeException("User stats for user with username " + username + " not found");
        }
    }

    @Override
    public User getUserFromToken(String token) {
        String email = tokenProvider.getEmailFromToken(token);
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
    }

    @Override
    public Optional<User> findUserByResetToken(String resetToken) {
        return userRepository.findByResetToken(resetToken);
    }

    @Override
    public User findUserByEmail(final String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public boolean checkIfValidOldPassword(final User user, final String oldPassword) {
        return passwordEncoder.matches(oldPassword, user.getPassword());
    }

    @Override
    public void changeUserPassword(final User user, final String password) {
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    @Override
    public User updateUser(final UserDto userDto) {
        Optional<User> user = userRepository.findById(userDto.getId());
        user.ifPresent(t -> {
            Optional.ofNullable(userDto.getFirstName())
                    .ifPresent(t::setFirstName);
            Optional.ofNullable(userDto.getCity())
                    .ifPresent(t::setCity);
            Optional.ofNullable(userDto.getFacebookUrl())
                    .ifPresent(t::setFacebookUrl);
            Optional.ofNullable(userDto.getYoutubeUrl())
                    .ifPresent(t::setYoutubeUrl);
            userRepository.save(t);
        });
        return userRepository
                .findById(userDto.getId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id:" + userDto.getId()));
    }
}
