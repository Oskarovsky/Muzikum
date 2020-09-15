package com.oskarro.muzikum.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserStatisticsRepository extends JpaRepository<UserStatistics, Integer> {

    UserStatistics save(UserStatistics userStatistics);

    Optional<UserStatistics> findById(Integer id);
}
