package me.kiryakov.animal_chips.mapper;

import me.kiryakov.animal_chips.domain.Location;
import me.kiryakov.animal_chips.dto.LocationDTO;
import org.springframework.stereotype.Component;

@Component
public class LocationMapper {
    public LocationDTO toDTO(Location location) {
        LocationDTO locationDTO = new LocationDTO();
        locationDTO.setId(location.getId());
        locationDTO.setLatitude(location.getLatitude());
        locationDTO.setLongitude(location.getLongitude());
        return locationDTO;
    }
}
