package me.kiryakov.animal_chips.service;

import me.kiryakov.animal_chips.domain.AnimalType;
import me.kiryakov.animal_chips.dto.AnimalTypeDTO;
import me.kiryakov.animal_chips.exception.NotFoundException;
import me.kiryakov.animal_chips.repository.AnimalTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AnimalTypeService {

    @Autowired
    private AnimalTypeRepository animalTypeRepository;

    public AnimalTypeDTO create(AnimalTypeDTO animalTypeDTO) {
        AnimalType animalType = new AnimalType();
        animalType.setType(animalTypeDTO.getType());
        animalTypeRepository.save(animalType);
        AnimalTypeDTO dto = new AnimalTypeDTO();
        dto.setId(animalType.getId());
        dto.setType(animalType.getType());
        return dto;
    }

    public AnimalTypeDTO getById(Long id) {
        Optional<AnimalType> optional = animalTypeRepository.findById(id);
        if (optional.isPresent()) {
            AnimalType animalType = optional.get();
            AnimalTypeDTO dto = new AnimalTypeDTO();
            dto.setId(animalType.getId());
            dto.setType(animalType.getType());
            return dto;
        }
        throw new NotFoundException("No animal type found with id " + id);
    }

    public AnimalTypeDTO editAnimalType(Long id, AnimalTypeDTO animalTypeDTO) {
        Optional<AnimalType> optional = animalTypeRepository.findById(id);
        if (optional.isPresent()) {
            AnimalType animalType = optional.get();
            animalType.setType(animalTypeDTO.getType());
            animalTypeRepository.save(animalType);
            return animalTypeDTO;
        }
        throw new NotFoundException("No animal type found with id " + id);
    }
    public void deleteAnimalType(Long id) {
        Optional<AnimalType> optional = animalTypeRepository.findById(id);
        if (optional.isPresent()) {
            AnimalType animalType = optional.get();
            animalTypeRepository.delete(animalType);
        }
    }
}
