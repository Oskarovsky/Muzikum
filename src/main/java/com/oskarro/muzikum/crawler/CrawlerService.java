package com.oskarro.muzikum.crawler;

import com.oskarro.muzikum.provider.Provider;
import com.oskarro.muzikum.track.Genre;
import com.oskarro.muzikum.track.Track;
import com.oskarro.muzikum.track.TrackService;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class CrawlerService {

    TrackService trackService;

    public CrawlerService(TrackService trackService) {
        this.trackService = trackService;
    }

    public String getWeb(Provider provider, Genre genre) {
        return null;
    }

    public String parseWeb(Provider provider) {
        String urlSite = "tracks/club_house/1y";
        try {
            Elements formsList = Jsoup.connect(provider.getUrl() + urlSite)
                    .get()
                    .getElementsByClass("title");

            System.out.println(formsList);

            for (Element element : formsList) {
                String name = element.getElementsByTag("a").text();
                Track track = Track.builder()
                        .url(element.getElementsByTag("a").first().attr("href"))
                        .provider(provider)
                        .build();

                if (name.contains("-")) {
                    track.setArtist(name.split("-")[0]);
                    track.setTitle(name.split("-")[1]);
                } else {
                    track.setTitle(name);
                }

                if (name.contains("(")) {
                    track.setVersion(name.substring(name.indexOf("(")+1, name.indexOf(")")));
                } else {
                    track.setVersion("Original Mix");
                }
                trackService.saveTrack(track);
            }
            return "All tracklist has been fetched from Billboard.com";
        } catch (IOException e) {
            log.error(String.format("There are a problem with parsing website: %s", provider.getName()));
            e.printStackTrace();
            return null;
        }
    }
}
