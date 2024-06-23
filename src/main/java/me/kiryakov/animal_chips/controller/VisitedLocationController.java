package me.kiryakov.animal_chips.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import me.kiryakov.animal_chips.dto.EditVisitedLocationDTO;
import me.kiryakov.animal_chips.dto.VisitedLocationDTO;
import me.kiryakov.animal_chips.dto.VisitedLocationSearchDTO;
import me.kiryakov.animal_chips.service.VisitedLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping(path = "/animals/{animalId}/locations")
public class VisitedLocationController {

    @Autowired
    private VisitedLocationService visitedLocationService;

    @PostMapping("/{pointID}")
    public ResponseEntity<VisitedLocationDTO> createVisitedLocation(@PathVariable @Min(1) Long animalId, @PathVariable @Min(1) Long pointID) {
        VisitedLocationDTO visitedLocation = visitedLocationService.createVisitedLocation(animalId, pointID);
        return new ResponseEntity<>(visitedLocation, HttpStatus.CREATED);
    }
    @GetMapping
    public List<VisitedLocationDTO> getAllVisitedLocations(@PathVariable @Min(1) Long animalId, @Valid VisitedLocationSearchDTO dto) {
        return visitedLocationService.getVisitedLocations(animalId, dto);
    }
    @PutMapping
    public VisitedLocationDTO editVisitedLocation(@PathVariable @Min(1) Long animalId,@RequestBody @Valid EditVisitedLocationDTO dto){
        return visitedLocationService.editVisitedLocation(animalId,dto);
    }
    @DeleteMapping("/{visitedPointId}")
    public void deleteVisitedLocation(@PathVariable @Min(1) Long animalId, @PathVariable @Min(1) Long visitedPointId) {
        visitedLocationService.deleteVisitedLocations(animalId, visitedPointId);
    }
}
