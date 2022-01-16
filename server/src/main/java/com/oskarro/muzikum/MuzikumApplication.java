package com.oskarro.muzikum;

import com.oskarro.muzikum.config.AppProperties;
import com.oskarro.muzikum.demo.DemoService;
import com.oskarro.muzikum.demo.ProdService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Objects;
import java.util.TimeZone;


@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
@EntityScan(basePackageClasses = {
        MuzikumApplication.class,
        Jsr310JpaConverters.class
})
@EnableAsync
@EnableScheduling
@EnableConfigurationProperties(AppProperties.class)

public class MuzikumApplication implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(MuzikumApplication.class);

    @PostConstruct
    void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(MuzikumApplication.class, args);
        Environment env = applicationContext.getEnvironment();
        logApplicationStartup(env);

        if (Objects.equals(env.getProperty("spring.profiles.active"), "dev")) {
            DemoService demoService = applicationContext.getBean(DemoService.class);
            demoService.createSamples();
        } else if (Objects.equals(env.getProperty("spring.profiles.active"), "prod")) {
            ProdService demoService = applicationContext.getBean(ProdService.class);
            demoService.createInitData();
        } else {
            ProdService demoService = applicationContext.getBean(ProdService.class);
            demoService.createInitData();
        }
    }

    private static void logApplicationStartup(Environment env) {
        String protocol = "http";
        if (env.getProperty("server.ssl.key-store") != null) {
            protocol = "https";
        }
        String serverPort = env.getProperty("server.port");
        String contextPath = env.getProperty("server.servlet.context-path");
        if (StringUtils.isBlank(contextPath)) {
            contextPath = "/";
        }
        String hostAddress = "localhost";
        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            logger.warn("The host name could not be determined, using `localhost` as fallback");
        }
        logger.info("""
                        ----------------------------------------------------------
                        \tApplication '{}' is running! Access URLs:
                        \tLocal: \t\t{}://localhost:{}{}
                        \tExternal: \t{}://{}:{}{}
                        \tProfile(s): \t{}
                        ----------------------------------------------------------""",
                env.getProperty("spring.application.name"),
                protocol,
                serverPort,
                contextPath,
                protocol,
                hostAddress,
                serverPort,
                contextPath,
                env.getActiveProfiles());
    }

    @Override
    public void run(String... arg) {

    }
}
