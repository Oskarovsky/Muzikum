package com.oskarro.muzikum.config;

import com.oskarro.muzikum.plugin.PluginService;
import com.oskarro.muzikum.plugin.PluginServiceImpl;
import com.oskarro.muzikum.user.UserDetailsServiceImpl;
import com.oskarro.muzikum.user.UserService;
import org.springframework.context.annotation.Bean;

@org.springframework.boot.test.context.TestConfiguration
public class TestConfiguration {

    @Bean
    public PluginService pluginService() {
        return new PluginServiceImpl();
    }

    @Bean
    public UserService userService() {
        return new UserDetailsServiceImpl(userRepository, userStatisticsRepository, passwordEncoder, tokenProvider);
    }

}
