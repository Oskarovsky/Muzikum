package com.oskarro.muzikum.provider.contractor;

import com.oskarro.muzikum.provider.Provider;
import com.oskarro.muzikum.track.Genre;
import com.oskarro.muzikum.track.Track;
import com.oskarro.muzikum.track.TrackService;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class DanceChartServiceImpl implements DanceChartService {

    TrackService trackService;

    public DanceChartServiceImpl(TrackService trackService) {
        this.trackService = trackService;
    }

    @Override
    public String getTrackList(Provider provider) {
        try {
            Elements formsList = Jsoup.connect(provider.getUrl())
                    .get()
                    .getElementById("charts")
                    .getElementsByTag("tbody").get(0)
                    .getElementsByClass("dc-list");

            for (Element element : formsList) {
                String title = element.getElementsByClass("song_new").text();
                Track track = Track.builder()
                        .artist(element.getElementsByClass("artist_new").text())
                        .title(element.getElementsByClass("song_new").text().split("\\(")[0])
                        .genre(Genre.dance.toString())
                        .provider(provider)
                        .build();
                if (title.contains("(")) {
                    track.setVersion(title.substring(title.indexOf("(")+1, title.indexOf(")")));
                } else {
                    track.setVersion("Radio Edit");
                }
                trackService.saveTrack(track);
            }
            return "All tracklist has been fetched from DanceChart.de";
        } catch (IOException e) {
            log.error(String.format("There are a problem with parsing website: %s", provider.getName()));
            e.printStackTrace();
            return null;
        }
    }
}
