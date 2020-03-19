package com.oskarro.muzikum.provider;

import com.oskarro.muzikum.crawler.CrawlerService;
import com.oskarro.muzikum.provider.contractor.NuteczkiService;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class ProviderControllerTest {

    @Autowired
    ProviderService providerService;

    @Autowired
    NuteczkiService nuteczkiService;

    @Autowired
    CrawlerService crawlerService;


    @BeforeEach
    void setUp() {
    }

    @Test
    void getCrawler() {
        Provider provider = Provider.builder().id(1).description("nice").url("https://nuteczki.eu/").name("nuteczki").build();
        crawlerService.parseWeb(provider);
    }

    @Test
    void getNuteczkiTracklist() {
/*        Provider provider = Provider.builder().id(1).description("nice").url("https://nuteczki.eu/").name("nuteczki").build();
        Document document = crawlerService.parseWeb(provider);
        Elements trackList = document.select("div.top-music-list");
        nuteczkiService.getTrackList(provider);*/
    }
}
