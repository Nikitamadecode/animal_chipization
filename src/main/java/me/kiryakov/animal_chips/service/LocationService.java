package me.kiryakov.animal_chips.service;

import me.kiryakov.animal_chips.domain.Location;
import me.kiryakov.animal_chips.dto.LocationDTO;
import me.kiryakov.animal_chips.exception.NotFoundException;
import me.kiryakov.animal_chips.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    public LocationDTO create(LocationDTO locationDTO) {
        Location location = new Location();
        location.setLongitude(locationDTO.getLongitude());
        location.setLatitude(locationDTO.getLatitude());
        locationRepository.save(location);
        LocationDTO dto = new LocationDTO();
        dto.setId(location.getId());
        dto.setLongitude(locationDTO.getLongitude());
        dto.setLatitude(locationDTO.getLatitude());
        return dto;
    }

    public LocationDTO getLocationById(Long id) {
        Optional<Location> optional = locationRepository.findById(id);
        if (optional.isPresent()) {
            Location location = optional.get();
            LocationDTO dto = new LocationDTO();
            dto.setId(location.getId());
            dto.setLongitude(location.getLongitude());
            dto.setLatitude(location.getLatitude());
            return dto;
        }
        throw new NotFoundException("No animal location found with id " + id);
    }

    public LocationDTO editAnimalLocation(Long id, LocationDTO locationDTO) {
        Optional<Location> optional = locationRepository.findById(id);
        if (optional.isPresent()) {
            Location location = optional.get();
            location.setLongitude(locationDTO.getLongitude());
            location.setLatitude(locationDTO.getLatitude());
            locationRepository.save(location);
            return locationDTO;
        }
        throw new NotFoundException("No animal location found with id " + id);
    }

    public void deleteAnimalLocation(Long id) {
        Optional<Location> optional = locationRepository.findById(id);
        if (optional.isPresent()) {
            Location location = optional.get();
            locationRepository.delete(location);
        }
    }
}
