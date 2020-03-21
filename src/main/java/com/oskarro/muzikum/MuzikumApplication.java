package com.oskarro.muzikum;

import com.oskarro.muzikum.crawler.CrawlerService;
import com.oskarro.muzikum.provider.Provider;
import com.oskarro.muzikum.provider.ProviderRepository;
import com.oskarro.muzikum.track.Track;
import com.oskarro.muzikum.track.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Stream;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class MuzikumApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(MuzikumApplication.class, args);

        CrawlerService crawlerService = applicationContext.getBean(CrawlerService.class);
        ProviderRepository providerRepository = applicationContext.getBean(ProviderRepository.class);

        providerRepository.saveAll(Collections.singletonList(
                Provider.builder().id(1).description("nice").url("https://nuteczki.eu/top20/#").name("nuteczki").build()
        ));

        Optional<Provider> provider = providerRepository.findById(1);

        //System.out.println(provider.map(crawlerService::parseWeb).toString());
        System.out.println(provider.map((Provider provider1) -> crawlerService.getWeb(provider1, "club")).toString());

    }

}
