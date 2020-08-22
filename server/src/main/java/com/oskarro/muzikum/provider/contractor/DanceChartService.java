package com.oskarro.muzikum.provider.contractor;

import com.oskarro.muzikum.provider.Provider;
import com.oskarro.muzikum.track.model.Genre;

public interface DanceChartService {

    String getTrackList(Provider provider, String urlPart, Genre genre);

    String getTracklistByGenre(Provider provider, Genre genre);
}
