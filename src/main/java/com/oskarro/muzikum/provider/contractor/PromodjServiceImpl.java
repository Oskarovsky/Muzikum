package com.oskarro.muzikum.provider.contractor;

import com.oskarro.muzikum.provider.Provider;
import com.oskarro.muzikum.track.Genre;
import org.springframework.stereotype.Service;

@Service
public class PromodjServiceImpl implements PromodjService {

    @Override
    public String getTrackList(Provider provider, String urlPart, Genre genre) {
        return null;
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
