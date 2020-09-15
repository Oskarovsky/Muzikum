package com.oskarro.muzikum.user;

import com.oskarro.muzikum.track.model.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    String USERS_BY_LOGIN_CACHE = "usersByLogin";
    String USERS_BY_EMAIL_CACHE = "usersByEmail";

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    Optional<User> findByUsernameOrEmail(String username, String email);

//    @Cacheable(cacheNames = USERS_BY_EMAIL_CACHE)
//    Optional<User> findOneByEmailIgnoreCase(String email);
//
//    Optional<User> findOneByResetKey(String resetKey);

    List<User> findByIdIn(Collection<Integer> id);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    Optional<User> findByResetToken(String resetToken);

    User save(User user);

    List<User> findAllByOrderByCreatedAtDesc();

}
