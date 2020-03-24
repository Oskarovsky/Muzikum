package com.oskarro.muzikum.provider.contractor;

import com.oskarro.muzikum.provider.Provider;
import com.oskarro.muzikum.track.Genre;
import org.springframework.stereotype.Service;

@Service
public class PromodjServiceImpl implements PromodjService {

    @Override
    public String getTrackList(Provider provider) {
        return null;
    }

    @Override
    public String getTracklistByGenre(Provider provider, Genre genre) {
        return null;
    }
}
