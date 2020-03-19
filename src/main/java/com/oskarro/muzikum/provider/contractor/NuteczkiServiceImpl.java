package com.oskarro.muzikum.provider.contractor;

import com.oskarro.muzikum.crawler.CrawlerService;
import com.oskarro.muzikum.provider.Provider;
import com.oskarro.muzikum.track.Track;
import com.oskarro.muzikum.track.TrackService;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class NuteczkiServiceImpl implements NuteczkiService {

    CrawlerService crawlerService;
    TrackService trackService;

    public NuteczkiServiceImpl(CrawlerService crawlerService, TrackService trackService) {
        this.crawlerService = crawlerService;
        this.trackService = trackService;
    }

    @Override
    public void getDocument(Provider provider) {
        crawlerService.parseWeb(provider);
    }

    @Override
    public String getTrackList(Provider provider) {
        try {
            Document document =  Jsoup.connect(provider.getUrl()).get();
            Elements views = document.getElementsByClass("view");
            for (Element view : views) {
                Element element = view.getElementsByTag("a").first();
                Element element1 = element.getElementsByTag("img").first();
                System.out.println(element1.attr("alt"));
                System.out.println(element.attr("href"));
                Track track = Track.builder()
                        .title(element1.attr("alt"))
                        .artist(element.attr("href"))
                        .build();
                trackService.saveTrack(track);
            }
            return "All tracklist has been fetched from nuteczki.eu";
        } catch (IOException e) {
            log.error(String.format("There are a problem with parsing website: %s", provider.getName()));
            e.printStackTrace();
            return null;
        }
    }
}
