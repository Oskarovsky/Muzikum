package com.oskarro.muzikum.mapper;

import com.oskarro.muzikum.track.model.TrackDto;
import com.oskarro.muzikum.track.model.Track;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TrackMapper {

    TrackDto mapTrackToDto(Track track);

    @InheritInverseConfiguration
    Track mapDtoToTrack(TrackDto trackDto);
}
