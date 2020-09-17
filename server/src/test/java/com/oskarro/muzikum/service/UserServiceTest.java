package com.oskarro.muzikum.service;

import com.oskarro.muzikum.user.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

@Import(com.oskarro.muzikum.config.TestConfiguration.class)
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    UserStatisticsRepository userStatisticsRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    public void test_getTopUploaders() {
        System.out.println("START");
        for (User user : userStatisticsRepository.findTotalTopUploaderTotal()) {
            System.out.println(user.getId() + "/" + user.getUsername() + "  points:");
        }
        System.out.println("STOP");
    }


}
