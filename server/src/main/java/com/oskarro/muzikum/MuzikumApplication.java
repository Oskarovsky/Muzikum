package com.oskarro.muzikum;

import com.oskarro.muzikum.article.comment.CommentRepository;
import com.oskarro.muzikum.article.post.PostRepository;
import com.oskarro.muzikum.article.post.PostService;
import com.oskarro.muzikum.config.AppProperties;
import com.oskarro.muzikum.crawler.CrawlerService;
import com.oskarro.muzikum.demo.DemoService;
import com.oskarro.muzikum.playlist.PlaylistRepository;
import com.oskarro.muzikum.playlist.PlaylistService;
import com.oskarro.muzikum.provider.ProviderRepository;
import com.oskarro.muzikum.provider.contractor.*;
import com.oskarro.muzikum.storage.FilesStorageService;
import com.oskarro.muzikum.storage.ImageRepository;
import com.oskarro.muzikum.track.TrackCommentRepository;
import com.oskarro.muzikum.track.TrackRepository;
import com.oskarro.muzikum.track.TrackService;
import com.oskarro.muzikum.user.*;
import com.oskarro.muzikum.user.favorite.FavoriteTrackRepository;
import com.oskarro.muzikum.user.role.RoleRepository;
import com.oskarro.muzikum.video.VideoRepository;
import com.oskarro.muzikum.video.VideoService;
import com.oskarro.muzikum.voting.VotingRepository;
import com.oskarro.muzikum.voting.VotingService;
import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;


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

    private final Environment env;

    public MuzikumApplication(Environment env) {
        this.env = env;
    }

    @PostConstruct
    void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }


    @Bean
    public ServletWebServerFactory servletContainer() {
        // Enable SSL Traffic
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
            @Override
            protected void postProcessContext(Context context) {
                SecurityConstraint securityConstraint = new SecurityConstraint();
                securityConstraint.setUserConstraint("CONFIDENTAL");
                SecurityCollection collection = new SecurityCollection();
                collection.addPattern("/*");
                securityConstraint.addCollection(collection);
                context.addConstraint(securityConstraint);
            }
        };

        // Add HTTP to HTTPS redirect
        tomcat.addAdditionalTomcatConnectors(httpToHttpsRedirectConnector());
        return tomcat;
    }


/*    We need to redirect from HTTP to HTTPS. Without SSL, this application used
    port 8080. With SSL it will use port 8443. So, any request for 8082 needs to be
    redirected to HTTPS on 8443.*/

    private Connector httpToHttpsRedirectConnector() {
        Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
        connector.setScheme("http");
        connector.setPort(8080);
        connector.setSecure(false);
        connector.setRedirectPort(8443);
        return connector;
    }


//    @Resource
//    FilesStorageService storageService;

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(MuzikumApplication.class, args);
        Environment env = applicationContext.getEnvironment();
        logApplicationStartup(env);
        for (String profileName : env.getActiveProfiles()) {
            System.out.println("Currently active profile - " + profileName);
        }

        // BEANS
        CrawlerService crawlerService = applicationContext.getBean(CrawlerService.class);
        ProviderRepository providerRepository = applicationContext.getBean(ProviderRepository.class);
        DanceChartService danceChartService = applicationContext.getBean(DanceChartService.class);
        NuteczkiService nuteczkiService = applicationContext.getBean(NuteczkiService.class);
        RadiopartyService radiopartyService = applicationContext.getBean(RadiopartyService.class);
        BillboardService billboardService = applicationContext.getBean(BillboardService.class);
        PromodjService promodjService = applicationContext.getBean(PromodjService.class);
        MusicListService musicListService = applicationContext.getBean(MusicListService.class);
        AriaChartsService ariaChartsService = applicationContext.getBean(AriaChartsService.class);
        AppleService appleService = applicationContext.getBean(AppleService.class);
        PlaylistService playlistService = applicationContext.getBean(PlaylistService.class);
        PlaylistRepository playlistRepository = applicationContext.getBean(PlaylistRepository.class);
        TrackRepository trackRepository = applicationContext.getBean(TrackRepository.class);
        TrackService trackService = applicationContext.getBean(TrackService.class);
        UserDetailsService userDetailsService = applicationContext.getBean(UserDetailsService.class);
        RoleRepository roleRepository = applicationContext.getBean(RoleRepository.class);
        VideoRepository videoRepository = applicationContext.getBean(VideoRepository.class);
        VideoService videoService = applicationContext.getBean(VideoService.class);
        PasswordEncoder encoder = applicationContext.getBean(PasswordEncoder.class);
        UserRepository userRepository = applicationContext.getBean(UserRepository.class);
        PostRepository postRepository = applicationContext.getBean(PostRepository.class);
        PostService postService = applicationContext.getBean(PostService.class);
        VotingService votingService = applicationContext.getBean(VotingService.class);
        VotingRepository votingRepository = applicationContext.getBean(VotingRepository.class);
        FavoriteTrackRepository favoriteTrackRepository = applicationContext.getBean(FavoriteTrackRepository.class);
        UserService userService = applicationContext.getBean(UserService.class);
        UserStatisticsRepository userStatisticsRepository = applicationContext.getBean(UserStatisticsRepository.class);
        TrackCommentRepository trackCommentRepository = applicationContext.getBean(TrackCommentRepository.class);
        ImageRepository imageRepository = applicationContext.getBean(ImageRepository.class);
        FilesStorageService filesStorageService = applicationContext.getBean(FilesStorageService.class);
        CommentRepository commentRepository = applicationContext.getBean(CommentRepository.class);
        DemoService demoService = applicationContext.getBean(DemoService.class);

        demoService.createSamples();
//        demoService.createProviders();

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
        logger.info("\n----------------------------------------------------------\n\t" +
                        "Application '{}' is running! Access URLs:\n\t" +
                        "Local: \t\t{}://localhost:{}{}\n\t" +
                        "External: \t{}://{}:{}{}\n\t" +
                        "Profile(s): \t{}\n----------------------------------------------------------",
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
    public void run(String... arg) throws Exception {
//        storageService.deleteAll();
//        storageService.init();
    }
}
