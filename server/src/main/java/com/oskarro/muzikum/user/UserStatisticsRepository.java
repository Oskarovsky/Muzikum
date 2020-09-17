package com.oskarro.muzikum.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserStatisticsRepository extends JpaRepository<UserStatistics, Integer> {

    UserStatistics save(UserStatistics userStatistics);

    Optional<UserStatistics> findById(Integer id);

    Optional<UserStatistics> findByUserId(Integer userId);

    @Query("SELECT u " +
            "FROM UserStatistics us " +
            "JOIN us.user u " +
            "WHERE u.id = us.user.id AND us.totalUpload = (SELECT MAX(uss.totalUpload) FROM UserStatistics uss)")
    List<User> findTotalTopUploaderTotal();

    @Query("SELECT u " +
            "FROM UserStatistics us " +
            "JOIN us.user u " +
            "WHERE u.id = us.user.id AND us.monthUpload = (SELECT MAX(uss.monthUpload) FROM UserStatistics uss)")
    List<User> findMonthlyTopUploaderTotal();

    @Query("SELECT u " +
            "FROM UserStatistics us " +
            "JOIN us.user u " +
            "WHERE u.id = us.user.id AND us.weekUpload = (SELECT MAX(uss.weekUpload) FROM UserStatistics uss)")
    List<User> findWeeklyTopUploaderTotal();
}
