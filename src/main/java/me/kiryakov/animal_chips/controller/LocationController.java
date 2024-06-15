package me.kiryakov.animal_chips.controller;

import jakarta.validation.constraints.Min;
import me.kiryakov.animal_chips.dto.LocationDTO;
import me.kiryakov.animal_chips.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/locations")
public class LocationController {
    @Autowired
    private LocationService locationService;

    @PostMapping
    public LocationDTO addAnimalLocation(@RequestBody LocationDTO locationDTO) {
        return locationService.create(locationDTO);
    }
    @GetMapping(path = "/{typeId}")
    public LocationDTO getAnimalLocation(@PathVariable @Min(1) Long typeId) {
        return locationService.getLocationById(typeId);
    }
    @PutMapping (path = "/{typeId}")
    public LocationDTO updateAnimalLocation(@PathVariable @Min(1) Long typeId, @RequestBody LocationDTO locationDTO) {
        return locationService.editAnimalLocation(typeId, locationDTO);
    }
    @DeleteMapping(path = "/{typeId}")
    public void deleteAnimalLocation(@PathVariable @Min(1) Long typeId) {
        locationService.deleteAnimalLocation(typeId);
    }
}
