package com.oskarro.muzikum;

import com.oskarro.muzikum.crawler.CrawlerService;
import com.oskarro.muzikum.playlist.Playlist;
import com.oskarro.muzikum.playlist.PlaylistRepository;
import com.oskarro.muzikum.playlist.PlaylistService;
import com.oskarro.muzikum.provider.Provider;
import com.oskarro.muzikum.provider.ProviderRepository;
import com.oskarro.muzikum.provider.contractor.*;
import com.oskarro.muzikum.track.Genre;
import com.oskarro.muzikum.track.Track;
import com.oskarro.muzikum.track.TrackRepository;
import com.oskarro.muzikum.track.TrackService;
import com.oskarro.muzikum.user.Role;
import com.oskarro.muzikum.user.RoleName;
import com.oskarro.muzikum.user.RoleRepository;
import com.oskarro.muzikum.video.Category;
import com.oskarro.muzikum.video.Video;
import com.oskarro.muzikum.video.VideoRepository;
import com.oskarro.muzikum.video.VideoService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class MuzikumApplication {

    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*"); // this allows all origin
        config.addAllowedHeader("*"); // this allows all headers
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("HEAD");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("PATCH");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    public static void main(String[] args) {


        ApplicationContext applicationContext = SpringApplication.run(MuzikumApplication.class, args);

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

        // USERS CREATOR
        Role roleAdmin = new Role();
        roleAdmin.setName(RoleName.ROLE_ADMIN);
        Role rolePm = new Role();
        rolePm.setName(RoleName.ROLE_MODERATOR);
        Role roleUser = new Role();
        roleUser.setName(RoleName.ROLE_USER);
        roleRepository.saveAll(Arrays.asList(roleAdmin, rolePm, roleUser));



        // GENRE COLLECTIONS FOR PROVIDERS
        List<Genre> nuteczkiGenres = Stream.of(Genre.CLUB, Genre.DANCE).collect(Collectors.toList());
        List<Genre> radiopartyGenres = Stream.of(Genre.CLUB).collect(Collectors.toList());
        List<Genre> billboardGenres = Stream.of(Genre.DANCE).collect(Collectors.toList());
        List<Genre> appleGenres = Stream.of(Genre.CLUB, Genre.RETRO).collect(Collectors.toList());

        // DEFAULT PROVIDERS
        providerRepository.saveAll(Arrays.asList(
                Provider.builder().id(1).description("nice").url("https://nuteczki.eu/top20/#").name("nuteczki").genres(nuteczkiGenres).build(),
                Provider.builder().id(2).description("very nice").url("https://radioparty.pl/partylista.html").genres(radiopartyGenres).name("radioparty").build(),
                Provider.builder().id(3).description("sehr gut").url("https://www.dance-charts.de/").name("dancecharts").build(),
                Provider.builder().id(4).description("beautiful").url("https://www.billboard.com/charts/year-end/2019/dance-club-songs").genres(billboardGenres).name("billboard").build(),
                Provider.builder().id(5).description("super woop").url("https://promodj.com/top100/").name("promodj").build(),
                Provider.builder().id(6).description("bombastic").url("https://musiclist.com/en/").name("musiclist").build(),
                Provider.builder().id(7).description("nicename").url("https://www.ariacharts.com.au/").name("ariacharts").build(),
                Provider.builder().id(8).description("tasty service").url("https://music.apple.com/").genres(appleGenres).name("apple").build()
        ));

        Optional<Provider> nuteczkiProvider = providerRepository.findById(1);
        Optional<Provider> radiopartyProvider = providerRepository.findById(2);
        Optional<Provider> dancechartProvider = providerRepository.findById(3);
        Optional<Provider> billboardProvider = providerRepository.findById(4);
        Optional<Provider> promodjProvider = providerRepository.findById(5);
        Optional<Provider> musicListProvider = providerRepository.findById(6);
        Optional<Provider> ariaChartsProvider = providerRepository.findById(7);
        Optional<Provider> appleProvider = providerRepository.findById(8);

        // TRACKS FETCHING FROM EXTERNAL SERVICES
        radiopartyProvider.map(radiopartyService::getTrackList);
        billboardProvider.map(billboardService::getTrackList);

        // TODO implementation fetching all genres
//        nuteczkiProvider.map(provider -> nuteczkiService.getTracklistByGenre(provider, Genre.club));

//        dancechartProvider.map(provider -> danceChartService.getTracklistByGenre(provider, Genre.club));

/*      dancechartProvider.map(provider -> danceChartService.getTracklistByGenre(provider, Genre.house));
        dancechartProvider.map(provider -> danceChartService.getTracklistByGenre(provider, Genre.handsup));
        dancechartProvider.map(provider -> danceChartService.getTracklistByGenre(provider, Genre.dance));
        dancechartProvider.map(provider -> danceChartService.getTracklistByGenre(provider, Genre.techno));*/

/*        promodjProvider.map(provider -> promodjService.getTracklistByGenre(provider, Genre.dance));
        promodjProvider.map(provider -> promodjService.getTracklistByGenre(provider, Genre.club));
        promodjProvider.map(provider -> promodjService.getTracklistByGenre(provider, Genre.house));
        promodjProvider.map(provider -> promodjService.getTracklistByGenre(provider, Genre.electroHouse));
        promodjProvider.map(provider -> promodjService.getTracklistByGenre(provider, Genre.techno));
        promodjProvider.map(provider -> promodjService.getTracklistByGenre(provider, Genre.set));*/

/*        musicListProvider.map(provider -> musicListService.getTracklistByGenre(provider, Genre.dance));
        musicListProvider.map(provider -> musicListService.getTracklistByGenre(provider, Genre.house));
        musicListProvider.map(provider -> musicListService.getTracklistByGenre(provider, Genre.techno));*/

/*
        ariaChartsProvider.map(provider -> ariaChartsService.getTracklistByGenre(provider, Genre.dance));
        ariaChartsProvider.map(provider -> ariaChartsService.getTracklistByGenre(provider, Genre.club));
*/

        appleProvider.map(provider -> appleService.getTracklistByGenre(provider, Genre.RETRO));
        appleProvider.map(provider -> appleService.getTracklistByGenre(provider, Genre.TRANCE));
        appleProvider.map(provider -> appleService.getTracklistByGenre(provider, Genre.CLUB));


        //System.out.println(appleProvider.map(crawlerService::parseWeb).toString());
        //System.out.println(ariaChartsProvider.map((Provider provider1) -> crawlerService.getWeb(provider1, Genre.club)).toString());

        // PLAYLIST CREATING
        Playlist playlist = Playlist.builder().id(1).name("MyTop").build();
        Playlist playlist2 = Playlist.builder().id(2).name("SecondTop").build();
        playlistService.addPlaylist(playlist);
        playlistService.addPlaylist(playlist2);

        Track track1 = Track.builder().id(3).title("This is my test").artist("Mega test").version("Radio edit").playlist(playlist).build();
        Track track2 = Track.builder().id(5).title("next tes").artist("Mega test").version("Radio edit").playlist(playlist).build();
        Track track3 = Track.builder().id(7).title("ddfdf my test").artist("super").version("Extended edit").playlist(playlist2).build();
        Track track4 = Track.builder().id(1).title("This is More than ntht").artist("Mega tdsdsest").version("Remix").playlist(playlist2).build();
        Track track5 = Track.builder().id(4).title("This is my test").artist("Mega oss").version("dsd edit").playlist(playlist).build();

        Track track6 = Track.builder().title("L'Italiano").artist("The Sicilians ft. Angelo Venuto")
                .version("The DJ Serg Remix").url("https://www.youtube.com/watch?v=hymoFuKK_Ac").playlist(playlist).build();
        Track track7 = Track.builder().title("This is my test").artist("Mega oss").version("dsd edit").playlist(playlist).build();
        Track track8 = Track.builder().title("This is my test").artist("Mega oss").version("dsd edit").playlist(playlist).build();
        Track track9 = Track.builder().title("This is my test").artist("Mega oss").version("dsd edit").playlist(playlist).build();


        // VIDEO PANE:
        Video video1 = Video.builder().id(1).name("Vixa").url("Dp--txMIGPI")
                .category(Category.MIX.toString()).build();
        Video video2 = Video.builder().id(2).name("Virus").url("MpWfj-2P-9M")
                .category(Category.MIX.toString()).build();
        Video video3 = Video.builder().id(3).name("L'Italiano").url("moFuKK_Ac")
                .category(Category.RETRO.name()).build();
        Video video4 = Video.builder().id(4).name("Luna Mix Vol. 9").url("WRooj5n80uo")
                .category(Category.LUNA_MIX.name()).build();

        videoRepository.saveAll(Arrays.asList(video1, video2, video3, video4));



        Track track01 = Track.builder().title("First shit title").artist("Med").version("dsd edit").video(video1).build();
        Track track02 = Track.builder().title("Firseconddtitle").artist("Msssed").version("dsd edit").video(video2).build();
        Track track03 = Track.builder().title("one tow three").artist("test").version("dsd edit").video(video2).build();

        trackRepository.saveAll(Arrays.asList(track1, track01, track02, track03, track4, track5));

    }

}
