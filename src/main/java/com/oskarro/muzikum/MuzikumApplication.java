package com.oskarro.muzikum;

import com.oskarro.muzikum.crawler.CrawlerService;
import com.oskarro.muzikum.provider.Provider;
import com.oskarro.muzikum.provider.ProviderRepository;
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

        NuteczkiService nuteczkiService = applicationContext.getBean(NuteczkiService.class);
        RadiopartyService radiopartyService = applicationContext.getBean(RadiopartyService.class);
        CrawlerService crawlerService = applicationContext.getBean(CrawlerService.class);
        ProviderRepository providerRepository = applicationContext.getBean(ProviderRepository.class);

        providerRepository.saveAll(Arrays.asList(
                Provider.builder().id(1).description("nice").url("https://nuteczki.eu/top20/#").name("nuteczki").build(),
                Provider.builder().id(2).description("nice").url("https://radioparty.pl/partylista.html").name("radioparty").build()
        ));

        Optional<Provider> nuteczkiProvider = providerRepository.findById(1);
        Optional<Provider> radiopartyProvider = providerRepository.findById(2);

        nuteczkiProvider.map(provider -> nuteczkiService.getTracklistByGenre(provider, Genre.club));
        radiopartyProvider.map(radiopartyService::getTrackList);


        //System.out.println(provider.map(crawlerService::parseWeb).toString());
        //System.out.println(provider.map((Provider provider1) -> crawlerService.getWeb(provider1, Genre.club)).toString());

    }

}
