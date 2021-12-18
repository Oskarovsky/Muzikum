package com.oskarro.muzikum.user;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Service class for managing users.
 */

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceNew {

    private final UserRepository userRepository;

}
