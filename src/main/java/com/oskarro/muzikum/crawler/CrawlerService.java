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
        try {
            Elements formsList = Jsoup.connect(provider.getUrl())
                    .get()
                    .getElementsByClass("tabcontent").get(0)
                    .getElementsByTag("form");

            for (Element element : formsList) {
                String artist = element.select("div").first().toString()
                        .split("\\n ")[1].split("<")[0]
                        .replaceAll("&amp;","&");
                String title = element.select("div").first()
                        .getElementsByTag("span").first()
                        .text();
                Track track = Track.builder()
                        .artist(artist.substring(artist.indexOf(" ")))
                        .title(title.split("\\(")[0])
                        .genre(Genre.club.toString())
                        .version(title.substring(title.indexOf("(")+1, title.indexOf(")")))
                        .provider(provider)
                        .build();


                trackService.saveTrack(track);
                        //.version(name.substring(name.indexOf("(")+1, name.indexOf(")"))))
            }



            return "All tracklist has been fetched from radioparty.pl";
        } catch (IOException e) {
            log.error(String.format("There are a problem with parsing website: %s", provider.getName()));
            e.printStackTrace();
            return null;
        }
    }

}
