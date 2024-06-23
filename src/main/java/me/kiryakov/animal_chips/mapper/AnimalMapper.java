package me.kiryakov.animal_chips.mapper;

import me.kiryakov.animal_chips.domain.Animal;
import me.kiryakov.animal_chips.dto.AnimalDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AnimalMapper {
    public AnimalDTO toDTO(Animal animal) {
        AnimalDTO animalDTO = new AnimalDTO();
        animalDTO.setId(animal.getId());
        animalDTO.setHeight(animal.getHeight());
        animalDTO.setLength(animal.getLength());
        animalDTO.setWeight(animal.getWeight());
        animalDTO.setGender(animal.getGender());
        animalDTO.setLifeStatus(animal.getLifeStatus());
        animalDTO.setChippingDateTime(animal.getChippingDateTime());
        animalDTO.setDeathDateTime(animal.getDeathDateTime());
        animalDTO.setChipperId(animal.getChipper().getId());
        animalDTO.setChippingLocationId(animal.getChippingLocation().getId());

        List<Long> types = new ArrayList<>();
        animal.getAnimalTypes().forEach(type -> types.add(type.getId()));
        animalDTO.setAnimalTypes(types);

        List<Long> visitedLocations = new ArrayList<>();
        animal.getVisitedLocations().forEach(location -> visitedLocations.add(location.getId()));
        animalDTO.setVisitedLocations(visitedLocations);

        return animalDTO;
    }
}
