package com.oskarro.muzikum.provider.contractor;


import com.oskarro.muzikum.provider.Provider;
import com.oskarro.muzikum.track.Genre;

public interface NuteczkiService {

    void getDocument(Provider provider);

    String getTrackList(Provider provider);

    String getTracklistByGenre(Provider provider, Genre genre);

}
