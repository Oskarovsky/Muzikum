package com.oskarro.muzikum.provider.contractor;

import com.oskarro.muzikum.crawler.CrawlerService;
import com.oskarro.muzikum.provider.Provider;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

@Service
public class NuteczkiServiceImpl implements NuteczkiService {

    CrawlerService crawlerService;

    public NuteczkiServiceImpl(CrawlerService crawlerService) {
        this.crawlerService = crawlerService;
    }

    @Override
    public void getDocument(Provider provider) {
        crawlerService.parseWeb(provider);
    }

    @Override
    public String getTrackList(Provider provider) {
        Document document = crawlerService.parseWeb(provider);
        Elements trackList = document.select("div.top-music-list");
        return trackList.toString();
    }
}
