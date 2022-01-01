package com.oskarro.muzikum.track;

import com.oskarro.muzikum.track.model.TrackFavorite;

import java.util.List;

public interface TrackFavoriteService {

    void addTrackToFavorite(Integer trackId, String username);

    List<TrackFavorite> getFavoriteTracksByUsername(final String username);
}
