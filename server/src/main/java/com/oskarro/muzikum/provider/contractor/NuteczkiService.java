package com.oskarro.muzikum.provider.contractor;


import com.oskarro.muzikum.provider.Provider;
import com.oskarro.muzikum.track.model.Genre;

public interface NuteczkiService {

    String getTrackList(Provider provider);

    String getTracklistByGenre(Provider provider, Genre genre);

}
