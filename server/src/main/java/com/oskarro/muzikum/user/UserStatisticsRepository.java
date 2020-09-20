package com.oskarro.muzikum.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserStatisticsRepository extends JpaRepository<UserStatistics, Integer> {

    UserStatistics save(UserStatistics userStatistics);

    Optional<UserStatistics> findById(Integer id);

    Optional<UserStatistics> findByUserId(Integer userId);

    Optional<UserStatistics> findByUserUsername(String username);

    @Query("SELECT u " +
            "FROM UserStatistics us " +
            "JOIN us.user u " +
            "WHERE u.id = us.user.id AND us.totalUpload = (SELECT MAX(uss.totalUpload) FROM UserStatistics uss)")
    List<User> findTotalTopUploader();

    @Query("SELECT u " +
            "FROM UserStatistics us " +
            "JOIN us.user u " +
            "WHERE u.id = us.user.id AND us.monthUpload = (SELECT MAX(uss.monthUpload) FROM UserStatistics uss)")
    List<User> findMonthlyTopUploader();

    @Query("SELECT u " +
            "FROM UserStatistics us " +
            "JOIN us.user u " +
            "WHERE u.id = us.user.id AND us.weekUpload = (SELECT MAX(uss.weekUpload) FROM UserStatistics uss)")
    List<User> findWeeklyTopUploader();

    @Query("SELECT u " +
            "FROM UserStatistics us " +
            "JOIN us.user u " +
            "WHERE u.id = us.user.id " +
            "ORDER BY us.totalUpload DESC")
    List<User> findTotalTopUploaders();

    @Query("SELECT u " +
            "FROM UserStatistics us " +
            "JOIN us.user u " +
            "WHERE u.id = us.user.id " +
            "ORDER BY us.monthUpload DESC")
    List<User> findMonthlyTopUploaders();

    @Query("SELECT u " +
            "FROM UserStatistics us " +
            "JOIN us.user u " +
            "WHERE u.id = us.user.id " +
            "ORDER BY us.weekUpload DESC")
    List<User> findWeeklyTopUploaders();

    @Query("SELECT COUNT(t) FROM Track t WHERE t.user.username = :username")
    Integer getNumberOfTracksAddedByUsername(@Param("username") String username);

    @Query("SELECT COUNT(t) FROM Track t WHERE t.user.id = :userId")
    Integer getNumberOfTracksAddedByUserId(@Param("userId") Integer userId);


}
