package me.kiryakov.animal_chips.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import me.kiryakov.animal_chips.dto.AnimalDTO;
import me.kiryakov.animal_chips.dto.AnimalSearchDTO;
import me.kiryakov.animal_chips.service.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public AnimalDTO addAnimal(@RequestBody AnimalDTO dto) {
        return animalService.create(dto);
    }

    @GetMapping(path = "/{animalId}")
    public AnimalDTO getAnimal(@PathVariable @Min(1) Long animalId) {
        return animalService.getById(animalId);
    }

    @PutMapping(path = "/{animalId}")
    public AnimalDTO updateAnimal(@PathVariable @Min(1) Long animalId, @RequestBody AnimalDTO dto) {
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
}
