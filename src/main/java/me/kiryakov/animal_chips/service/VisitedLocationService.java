package me.kiryakov.animal_chips.service;

import jakarta.persistence.criteria.CriteriaQuery;
import lombok.extern.slf4j.Slf4j;
import me.kiryakov.animal_chips.domain.LifeStatus;
import me.kiryakov.animal_chips.domain.Location;
import me.kiryakov.animal_chips.domain.VisitedLocation;
import me.kiryakov.animal_chips.dto.EditVisitedLocationDTO;
import me.kiryakov.animal_chips.dto.VisitedLocationDTO;
import me.kiryakov.animal_chips.dto.VisitedLocationSearchDTO;
import me.kiryakov.animal_chips.exception.DataConflictException;
import me.kiryakov.animal_chips.exception.NotFoundException;
import me.kiryakov.animal_chips.mapper.VisitedLocationMapper;
import me.kiryakov.animal_chips.repository.AnimalRepository;
import me.kiryakov.animal_chips.repository.EntityRepository;
import me.kiryakov.animal_chips.repository.LocationRepository;
import me.kiryakov.animal_chips.repository.VisitedLocationRepository;
import me.kiryakov.animal_chips.util.CriteriaManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
public class VisitedLocationService {
    @Autowired
    private VisitedLocationRepository visitedLocationRepository;
    @Autowired
    private VisitedLocationMapper visitedLocationMapper;
    @Autowired
    private AnimalRepository animalRepository;
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private CriteriaManager criteriaManager;
    @Autowired
    private EntityRepository entityRepository;

    public VisitedLocationDTO createVisitedLocation(Long animalId, Long locationId) {

        var animal = animalRepository.findById(animalId)
                .orElseThrow(() -> new NotFoundException("Animal not found"));
        var location = locationRepository.findById(locationId)
                .orElseThrow(() -> new NotFoundException("Location not found"));

        if (animal.getLifeStatus() == LifeStatus.DEAD) {
            throw new DataConflictException("You are already dead");
        }
        List<VisitedLocation> visitedLocations = animal.getVisitedLocations().stream()
                .sorted(Comparator.comparing(VisitedLocation::getDateTime))
                .toList();
        log.info("Visitedlocations.isEmpty{}", visitedLocations.isEmpty());
        if (!visitedLocations.isEmpty()) {
            log.info("last visited point {}", visitedLocations.getLast().getLocation().getId());

            if (visitedLocations.getLast().getLocation().getId().equals(locationId)) {
                throw new DataConflictException("Animal already visited this location");
            }
        } else if (animal.getChippingLocation().getId().equals(locationId)) {
            throw new DataConflictException("kakashki");
        }
        VisitedLocation visitedLocation = new VisitedLocation();
        visitedLocation.setAnimal(animal);
        visitedLocation.setLocation(location);
        visitedLocation.setDateTime(LocalDateTime.now());
        visitedLocationRepository.save(visitedLocation);
        return visitedLocationMapper.toDTO(visitedLocation);
    }

    public List<VisitedLocationDTO> getVisitedLocations(Long animalId, VisitedLocationSearchDTO dto) {
        var animal = animalRepository.findById(animalId)
                .orElseThrow(() -> new NotFoundException("Animal not found"));
        CriteriaQuery<VisitedLocation> criteria = criteriaManager.buildCriteriaForVisitedLocation(dto.getStartDateTime(), dto.getEndDateTime(), animal);
        List<VisitedLocation> visitedLocations = entityRepository.searchByCriteria(criteria, dto.getFrom(), dto.getSize());
        return visitedLocations.stream()
                .map(visitedLocation -> visitedLocationMapper.toDTO(visitedLocation))
                .toList();
    }

    public VisitedLocationDTO editVisitedLocation(Long animalId, EditVisitedLocationDTO dto) {

        log.info("animal id {}", animalId);
        log.info("visitedLocationPointId {}", dto.getVisitedLocationPointId());
        log.info("locationPointId {}", dto.getLocationPointId());

        var location = locationRepository.findById(dto.getLocationPointId())
                .orElseThrow(() -> new NotFoundException("Location not found"));
        var animal = animalRepository.findById(animalId)
                .orElseThrow(() -> new NotFoundException("Animal not found"));
        var visitedLocation = visitedLocationRepository.findById(dto.getVisitedLocationPointId())
                .orElseThrow(() -> new NotFoundException("Visited location not found"));

        if (!animal.getVisitedLocations().contains(visitedLocation)) {
            throw new NotFoundException("Animal has no this visited location");
        }

        List<VisitedLocation> visitedLocations = animal.getVisitedLocations().stream()
                .sorted(Comparator.comparing(VisitedLocation::getDateTime))
                .toList();
        if (visitedLocations.getFirst().getId().equals(dto.getVisitedLocationPointId())) {
            if (animal.getChippingLocation().getId().equals(dto.getLocationPointId())) {
                throw new DataConflictException("You are already kakashki");
            }
        }

        if(checkCollision(visitedLocations, visitedLocation, location)){
            throw new DataConflictException("visited location collision");
        }
        if(visitedLocation.getLocation().equals(location)){
            throw new DataConflictException("animal already in this location");
        }

        visitedLocation.setLocation(location);
        visitedLocationRepository.save(visitedLocation);
        return visitedLocationMapper.toDTO(visitedLocation);
    }

    public void deleteVisitedLocations(Long animalId, Long visitedLocationId) {
        var animal = animalRepository.findById(animalId)
                .orElseThrow(() -> new NotFoundException("Animal not found"));
        VisitedLocation visitedLocation = visitedLocationRepository.findById(visitedLocationId)
                .orElseThrow(() -> new NotFoundException("Visited location not found"));

        boolean exist = animal.getVisitedLocations().stream()
                .anyMatch(vl -> vl.getId().equals(visitedLocationId));
        if (!exist) {
            throw new NotFoundException("Visited location not found");
        }

        List<VisitedLocation> visitedLocations = animal.getVisitedLocations().stream()
                .sorted(Comparator.comparing(VisitedLocation::getDateTime))
                .toList();

        if (visitedLocations.getFirst().getId().equals(visitedLocationId) && visitedLocations.size() > 1) {
            if (visitedLocations.get(1).getLocation().getId().equals(animal.getChippingLocation().getId())) {
                visitedLocationRepository.delete(visitedLocations.get(1));
            }
        }

        visitedLocationRepository.delete(visitedLocation);
    }

    private boolean checkCollision(List<VisitedLocation> sortedVisitedLocations, VisitedLocation visitedLocation, Location location) {
        int index = sortedVisitedLocations.indexOf(visitedLocation);
        return isSimilarPref(sortedVisitedLocations, index, location) || isSimilarNext(sortedVisitedLocations, index, location);
    }

    private boolean isSimilarPref(List<VisitedLocation> sortedVisitedLocations, int index, Location location) {
        if(index > 0){
            return sortedVisitedLocations.get(index-1).getLocation().equals(location);
        }
        return false;
    }

    private boolean isSimilarNext(List<VisitedLocation> sortedVisitedLocations, int index, Location location) {
        if (index < sortedVisitedLocations.size() - 1) {
            return sortedVisitedLocations.get(index+1).getLocation().equals(location);
        }
        return false;
    }
}
