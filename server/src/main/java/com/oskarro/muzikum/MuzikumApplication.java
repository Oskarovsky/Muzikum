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
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class MuzikumApplication {

    @Bean
    public FilterRegistrationBean<CorsFilter> simpleCorsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
        config.setAllowedMethods(Collections.singletonList("*"));
        config.setAllowedHeaders(Collections.singletonList("*"));
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
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
        Playlist playlist = Playlist.builder().id(1).name("MyTop").tracks(new ArrayList<>()).build();
        Playlist playlist2 = Playlist.builder().id(2).name("SecondTop").tracks(new ArrayList<>()).build();
        playlistService.addPlaylist(playlist);
        playlistService.addPlaylist(playlist2);

        Track track1 = Track.builder().id(3).title("This is my test").artist("Mega test").version("Radio edit").build();
        Track track2 = Track.builder().id(5).title("next tes").artist("Mega test").version("Radio edit").build();
        Track track3 = Track.builder().id(7).title("ddfdf my test").artist("super").version("Extended edit").build();
        Track track4 = Track.builder().id(1).title("This is More than ntht").artist("Mega tdsdsest").version("Remix").build();
        Track track5 = Track.builder().id(4).title("This is my test").artist("Mega oss").version("dsd edit").build();

        playlistService.addTrackToPlaylist(track1, 1);
        playlistService.addTrackToPlaylist(track2, 1);
        playlistService.addTrackToPlaylist(track3, 2);
        playlistService.addTrackToPlaylist(track4, 2);
        playlistService.addTrackToPlaylist(track5, 1);


    }

}
