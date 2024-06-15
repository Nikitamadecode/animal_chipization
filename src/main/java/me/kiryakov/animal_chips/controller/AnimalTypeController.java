package me.kiryakov.animal_chips.controller;

import jakarta.validation.constraints.Min;
import me.kiryakov.animal_chips.dto.AnimalTypeDTO;
import me.kiryakov.animal_chips.service.AnimalTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/animals/types")
public class AnimalTypeController {
    @Autowired
    private AnimalTypeService animalTypeService;


    @PostMapping
    public AnimalTypeDTO addAnimalType(@RequestBody AnimalTypeDTO dto) {
        return animalTypeService.create(dto);
    }

    @GetMapping(path = "/{typeId}")
    public AnimalTypeDTO getAnimalTypeById(@PathVariable @Min(1) Long typeId) {
        return animalTypeService.getById(typeId);
    }

    @PutMapping(path = "/{typeId}")
    public AnimalTypeDTO updateAnimalTypeById(@PathVariable @Min(1) Long typeId, @RequestBody AnimalTypeDTO animalTypeDTO) {
        return animalTypeService.editAnimalType(typeId, animalTypeDTO);
    }

    @DeleteMapping(path = "/{typeId}")
    public void deleteAnimalTypeById(@PathVariable @Min(1) Long typeId) {
        animalTypeService.deleteAnimalType(typeId);
    }
}
