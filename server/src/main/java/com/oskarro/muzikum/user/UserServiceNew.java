package com.oskarro.muzikum.user;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service class for managing users.
 */

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceNew {

    private final UserRepository userRepository;




//    public Optional<User> completePasswordReset(String newPassword, String key) {
//        log.debug("Reset user password for reset key {}", key);
//        return userRepository
//                .findOneByResetKey(key)
//                .filter(user -> user.getResetDate().isAfter(Instant.now().minusSeconds(86400)))
//                .map(
//                        user -> {
//                            user.setPassword(passwordEncoder.encode(newPassword));
//                            user.setResetKey(null);
//                            user.setResetDate(null);
//                            userRepository.save(user);
//                            this.clearUserCaches(user);
//                            return user;
//                        }
//                );
//    }

//    public Optional<User> requestPasswordReset(String mail) {
//        return userRepository
//                .findOneByEmailIgnoreCase(mail)
//                .filter(User::getActivated)
//                .map(
//                        user -> {
//                            user.setResetKey(RandomUtil.generateResetKey());
//                            user.setResetDate(Instant.now());
//                            userRepository.save(user);
//                            this.clearUserCaches(user);
//                            return user;
//                        }
//                );
//    }

//    private void clearUserCaches(User user) {
//        Objects.requireNonNull(cacheManager.getCache(UserRepository.USERS_BY_LOGIN_CACHE)).evict(user.getUsername());
//        if (user.getEmail() != null) {
//            Objects.requireNonNull(cacheManager.getCache(UserRepository.USERS_BY_EMAIL_CACHE)).evict(user.getEmail());
//        }
//    }

}
