package me.kiryakov.animal_chips.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import me.kiryakov.animal_chips.dto.AnimalTypeDTO;
import me.kiryakov.animal_chips.service.AnimalTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping(path = "/animals/types")
public class AnimalTypeController {
    @Autowired
    private AnimalTypeService animalTypeService;


    @PostMapping
    public ResponseEntity<AnimalTypeDTO> addAnimalType(@RequestBody @Valid AnimalTypeDTO dto) {
        AnimalTypeDTO dto1 = animalTypeService.create(dto);
        return new ResponseEntity<>(dto1, HttpStatus.CREATED);
    }

    @GetMapping(path = "/{typeId}")
    public AnimalTypeDTO getAnimalTypeById(@PathVariable @Min(1) Long typeId) {
        return animalTypeService.getById(typeId);
    }

    @PutMapping(path = "/{typeId}")
    public AnimalTypeDTO updateAnimalTypeById(@PathVariable @Min(1) Long typeId, @RequestBody @Valid AnimalTypeDTO animalTypeDTO) {
        return animalTypeService.editAnimalType(typeId, animalTypeDTO);
    }

    @DeleteMapping(path = "/{typeId}")
    public void deleteAnimalTypeById(@PathVariable @Min(1) Long typeId) {
        animalTypeService.deleteAnimalType(typeId);
    }
}
