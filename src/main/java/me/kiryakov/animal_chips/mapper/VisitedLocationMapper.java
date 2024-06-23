package me.kiryakov.animal_chips.mapper;

import me.kiryakov.animal_chips.domain.VisitedLocation;
import me.kiryakov.animal_chips.dto.VisitedLocationDTO;
import org.springframework.stereotype.Component;

@Component
public class VisitedLocationMapper {
    public VisitedLocationDTO toDTO(VisitedLocation visitedLocation) {
        VisitedLocationDTO dto = new VisitedLocationDTO();
        dto.setId(visitedLocation.getId());
        dto.setLocationPointId(visitedLocation.getLocation().getId());
        dto.setDateTimeOfVisitLocationPoint(visitedLocation.getDateTime());
        return dto;
    }
}
