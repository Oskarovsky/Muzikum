package com.oskarro.muzikum.provider.contractor;

import com.oskarro.muzikum.provider.Provider;
import com.oskarro.muzikum.track.Genre;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AriaChartsServiceImpl implements AriaChartsService {

    @Override
    public String getTrackList(Provider provider, String urlPart, Genre genre) {
        return null;
    }

    @Override
    public String getTracklistByGenre(Provider provider, Genre genre) {
        switch (genre) {
            case dance:
                getTrackList(provider, "top-ventas-mexico/dance", genre);
                getTrackList(provider, "top-ventas-argentina/dance", genre);
                getTrackList(provider, "top-ventas-rusia/dance", genre);
                break;
            case house:
                getTrackList(provider, "house-2020", genre);
                break;
            case techno:
                getTrackList(provider, "techno", genre);
                break;
            default:
                log.info(String.format("Scrapper cannot find any tracks assigned %s genre", genre.toString()));
                return null;
        }
        return "success!";
    }
}
