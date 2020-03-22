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
    public String getTrackList(Provider provider, String urlPart, Genre genre) {
        try {
            Elements formsList = Jsoup.connect(provider.getUrl() + urlPart)
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

    @Override
    public String getTracklistByGenre(Provider provider, Genre genre) {
        switch (genre) {
            case dance:
                getTrackList(provider, "djcharts", genre);
                break;
            case bigroom:
                getTrackList(provider, "genrecharts/15", genre);
                break;
            case electroHouse:
                getTrackList(provider, "genrecharts/10", genre);
                break;
            case house:
                getTrackList(provider, "genrecharts/7", genre);
                getTrackList(provider, "genrecharts/29", genre);
                break;
            case techno:
                getTrackList(provider, "genrecharts/31", genre);
                break;
            case handsup:
                getTrackList(provider, "genrecharts/3", genre);
                break;
            default:
                log.info(String.format("Scrapper cannot find any tracks assigned %s genre", genre.toString()));
                return null;
        }
        return "success!";
    }
}
