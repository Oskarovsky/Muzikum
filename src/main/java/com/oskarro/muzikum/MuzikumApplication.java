package com.oskarro.muzikum;

import com.oskarro.muzikum.crawler.CrawlerService;
import com.oskarro.muzikum.provider.Provider;
import com.oskarro.muzikum.provider.ProviderRepository;
import com.oskarro.muzikum.provider.contractor.BillboardService;
import com.oskarro.muzikum.provider.contractor.DanceChartService;
import com.oskarro.muzikum.provider.contractor.NuteczkiService;
import com.oskarro.muzikum.provider.contractor.RadiopartyService;
import com.oskarro.muzikum.track.Genre;
import com.oskarro.muzikum.track.Track;
import com.oskarro.muzikum.track.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Stream;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class MuzikumApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(MuzikumApplication.class, args);

        // BEANS
        ProviderRepository providerRepository = applicationContext.getBean(ProviderRepository.class);
        DanceChartService danceChartService = applicationContext.getBean(DanceChartService.class);
        NuteczkiService nuteczkiService = applicationContext.getBean(NuteczkiService.class);
        RadiopartyService radiopartyService = applicationContext.getBean(RadiopartyService.class);
        CrawlerService crawlerService = applicationContext.getBean(CrawlerService.class);
        BillboardService billboardService = applicationContext.getBean(BillboardService.class);

        // DEFAULT PROVIDERS
        providerRepository.saveAll(Arrays.asList(
                Provider.builder().id(1).description("nice").url("https://nuteczki.eu/top20/#").name("nuteczki").build(),
                Provider.builder().id(2).description("very nice").url("https://radioparty.pl/partylista.html").name("radioparty").build(),
                Provider.builder().id(3).description("sehr gut").url("https://www.dance-charts.de/").name("dancecharts").build(),
                Provider.builder().id(4).description("beautiful").url("https://www.billboard.com/charts/year-end/2019/dance-club-songs").name("billboard").build(),
                Provider.builder().id(5).description("super woop").url("https://promodj.com/top100/").name("promodj").build()
        ));

        Optional<Provider> nuteczkiProvider = providerRepository.findById(1);
        Optional<Provider> radiopartyProvider = providerRepository.findById(2);
        Optional<Provider> dancechartProvider = providerRepository.findById(3);
        Optional<Provider> billboardProvider = providerRepository.findById(4);
        Optional<Provider> promodjProvider = providerRepository.findById(5);
/*
        // TRACKS FETCHING FROM EXTERNAL SERVICES
        radiopartyProvider.map(radiopartyService::getTrackList);
        billboardProvider.map(billboardService::getTrackList);

        // TODO implementation fetching all genres
        nuteczkiProvider.map(provider -> nuteczkiService.getTracklistByGenre(provider, Genre.club));

        dancechartProvider.map(provider -> danceChartService.getTracklistByGenre(provider, Genre.club));
        dancechartProvider.map(provider -> danceChartService.getTracklistByGenre(provider, Genre.house));
        dancechartProvider.map(provider -> danceChartService.getTracklistByGenre(provider, Genre.handsup));
        dancechartProvider.map(provider -> danceChartService.getTracklistByGenre(provider, Genre.dance));
        dancechartProvider.map(provider -> danceChartService.getTracklistByGenre(provider, Genre.techno));*/


        System.out.println(promodjProvider.map(crawlerService::parseWeb).toString());
        //System.out.println(provider.map((Provider provider1) -> crawlerService.getWeb(provider1, Genre.club)).toString());

    }

}
