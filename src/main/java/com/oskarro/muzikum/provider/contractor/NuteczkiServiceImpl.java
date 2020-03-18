package com.oskarro.muzikum.provider.contractor;

import com.oskarro.muzikum.crawler.CrawlerService;
import com.oskarro.muzikum.provider.Provider;
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
    public void getTrackList(Provider provider) {
        crawlerService.parseWeb(provider);

    }
}
