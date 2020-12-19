package com.oskarro.muzikum.mapper;

import com.oskarro.muzikum.dto.TrackDto;
import com.oskarro.muzikum.track.model.Track;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-12-19T22:45:33+0100",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 1.8.0_275 (Private Build)"
)
@Component
public class TrackMapperImpl implements TrackMapper {

    @Override
    public TrackDto mapTrackToDto(Track track) {
        if ( track == null ) {
            return null;
        }

        TrackDto trackDto = new TrackDto();

        trackDto.setId( track.getId() );
        trackDto.setTitle( track.getTitle() );
        trackDto.setArtist( track.getArtist() );
        trackDto.setPoints( track.getPoints() );
        trackDto.setGenre( track.getGenre() );
        trackDto.setVersion( track.getVersion() );
        trackDto.setUrl( track.getUrl() );
        trackDto.setPosition( track.getPosition() );

        return trackDto;
    }

    @Override
    public Track mapDtoToTrack(TrackDto trackDto) {
        if ( trackDto == null ) {
            return null;
        }

        Track track = new Track();

        track.setId( trackDto.getId() );
        track.setTitle( trackDto.getTitle() );
        track.setArtist( trackDto.getArtist() );
        track.setPoints( trackDto.getPoints() );
        track.setGenre( trackDto.getGenre() );
        track.setVersion( trackDto.getVersion() );
        track.setUrl( trackDto.getUrl() );
        track.setPosition( trackDto.getPosition() );

        return track;
    }
}
