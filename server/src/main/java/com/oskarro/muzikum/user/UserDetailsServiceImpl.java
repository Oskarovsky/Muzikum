package com.oskarro.muzikum.user;

import com.oskarro.muzikum.track.TrackRepository;
import com.oskarro.muzikum.track.model.Track;
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

    private UserRepository userRepository;
    private TrackRepository trackRepository;
    private UserStatisticsRepository userStatisticsRepository;

    public UserDetailsServiceImpl(UserRepository userRepository, TrackRepository trackRepository,
                                  UserStatisticsRepository userStatisticsRepository) {
        this.userRepository = userRepository;
        this.trackRepository = trackRepository;
        this.userStatisticsRepository = userStatisticsRepository;
    }

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
        UserStatistics userStatistics = userStatisticsRepository.findById(user.getId()).orElse(null);
        userStatistics.setMonthUpload(userStatistics.getMonthUpload() + 1);
        userStatistics.setWeekUpload(userStatistics.getWeekUpload() + 1);
        userStatistics.setTotalUpload(userStatistics.getTotalUpload() + 1);
    }


}
