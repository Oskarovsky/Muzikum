package com.oskarro.muzikum;

import com.oskarro.muzikum.track.Track;
import com.oskarro.muzikum.track.TrackRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.stream.Stream;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class MuzikumApplication {

    public static void main(String[] args) {
        SpringApplication.run(MuzikumApplication.class, args);

    }

}
