package com.oskarro.muzikum.crawler;

import com.oskarro.muzikum.provider.Provider;
import com.oskarro.muzikum.track.Genre;
import com.oskarro.muzikum.track.Track;
import com.oskarro.muzikum.track.TrackService;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONValue;
import org.json.JSONException;
import org.json.JSONObject;
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
        String urlSite = "techno";
        String urlYoutube = "https://www.youtube.com/watch?v=";
        try {
            Elements formsList = Jsoup.connect(provider.getUrl() + urlSite)
                    .get()
                    .getElementsByClass("songs-list-item");

            System.out.println(formsList);
            // https://www.youtube.com/watch?v=Y6V6MO2J3TQ
            for (Element element : formsList) {
                JSONObject json = new JSONObject(element.attr("data-player-song"));
                Track track = Track.builder()
                        .title(element.getElementsByTag("h4").text())
                        .url(urlYoutube + json.get("youtubeId"))
                        .provider(provider)
                        .build();
                trackService.saveTrack(track);
            }
            return "All tracklist has been fetched from Billboard.com";
        } catch (IOException e) {
            log.error(String.format("There are a problem with parsing website: %s", provider.getName()));
            e.printStackTrace();
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
