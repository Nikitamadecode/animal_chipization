package me.kiryakov.animal_chips.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import me.kiryakov.animal_chips.domain.Animal;
import me.kiryakov.animal_chips.dto.AnimalDTO;
import me.kiryakov.animal_chips.dto.AnimalSearchDTO;
import me.kiryakov.animal_chips.dto.EditAnimalTypeRequestDTO;
import me.kiryakov.animal_chips.service.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping(path = "/animals")
public class AnimalController {
    @Autowired
    private AnimalService animalService;

    @PostMapping
    public ResponseEntity<AnimalDTO> addAnimal(@RequestBody @Valid AnimalDTO dto) {
        AnimalDTO animalDTO = animalService.create(dto);
        return new ResponseEntity<>(animalDTO, HttpStatus.CREATED);
    }

    @GetMapping(path = "/{animalId}")
    public AnimalDTO getAnimal(@PathVariable @Min(1) Long animalId) {
        return animalService.getById(animalId);
    }

    @PutMapping(path = "/{animalId}")
    public AnimalDTO updateAnimal(@PathVariable @Min(1) Long animalId, @RequestBody @Valid AnimalDTO dto) {
        return animalService.update(animalId, dto);
    }

    @DeleteMapping(path = "/{animalId}")
    public void deleteAnimal(@PathVariable @Min(1) Long animalId) {
        animalService.delete(animalId);
    }

    @GetMapping(path = "/search")
    public List<AnimalDTO> search(@Valid AnimalSearchDTO dto) {
        return animalService.search(dto);
    }

    @PostMapping(path = "/{animalId}/types/{typeId}")
    public ResponseEntity<AnimalDTO> addAnimalType(@PathVariable @Min(1) Long animalId, @PathVariable @Min(1) Long typeId) {
        AnimalDTO animalDTO = animalService.addAnimalType(animalId, typeId);
        return new ResponseEntity<>(animalDTO, HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{animalId}/types/{typeId}")
    public AnimalDTO deleteAnimalType(@PathVariable @Min(1) Long animalId, @PathVariable @Min(1) Long typeId) {
        AnimalDTO animalDTO = animalService.deleteAnimalType(animalId, typeId);
        return animalDTO;
    }

    @PutMapping(path = "/{animalId}/types")
    public AnimalDTO editAnimalType(@PathVariable @Min(1) Long animalId, @RequestBody @Valid EditAnimalTypeRequestDTO dto) {
        AnimalDTO animalDTO = animalService.editAnimalType(animalId, dto);
        return animalDTO;
    }
}
