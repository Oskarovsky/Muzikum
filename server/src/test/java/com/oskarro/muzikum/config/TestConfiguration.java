package com.oskarro.muzikum.config;

import com.oskarro.muzikum.plugin.PluginService;
import com.oskarro.muzikum.plugin.PluginServiceImpl;
import com.oskarro.muzikum.security.jwt.JwtTokenProvider;
import com.oskarro.muzikum.user.UserDetailsServiceImpl;
import com.oskarro.muzikum.user.UserRepository;
import com.oskarro.muzikum.user.UserService;
import com.oskarro.muzikum.user.UserStatisticsRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Properties;

@org.springframework.boot.test.context.TestConfiguration
public class TestConfiguration {

    UserRepository userRepository;
    UserStatisticsRepository userStatisticsRepository;
    PasswordEncoder passwordEncoder;
    JwtTokenProvider tokenProvider;

    public TestConfiguration(UserRepository userRepository, UserStatisticsRepository userStatisticsRepository,
                             PasswordEncoder passwordEncoder, JwtTokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.userStatisticsRepository = userStatisticsRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    @Bean
    public PluginService pluginService() {
        return new PluginServiceImpl();
    }

    @Bean
    public UserService userService() {
        return new UserDetailsServiceImpl(userRepository, userStatisticsRepository, passwordEncoder, tokenProvider);
    }

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername("my.gmail@gmail.com");
        mailSender.setPassword("password");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }



}
