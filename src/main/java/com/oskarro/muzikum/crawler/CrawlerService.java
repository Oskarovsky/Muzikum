package com.oskarro.muzikum.crawler;

import com.oskarro.muzikum.provider.Provider;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class CrawlerService {

    public Document parseWeb(Provider provider) {
        try {
            Document document = Jsoup.connect(provider.getUrl()).get();
            return document;
        } catch (IOException e) {
            log.error(String.format("There are a problem with parsing website: %s", provider.getName()));
            e.printStackTrace();
            return null;
        }
    }


}
