package me.kiryakov.animal_chips.service;

import me.kiryakov.animal_chips.domain.AnimalType;
import me.kiryakov.animal_chips.dto.AnimalTypeDTO;
import me.kiryakov.animal_chips.exception.DataConflictException;
import me.kiryakov.animal_chips.exception.InaccessibleEntityException;
import me.kiryakov.animal_chips.exception.NotFoundException;
import me.kiryakov.animal_chips.mapper.AnimalMapper;
import me.kiryakov.animal_chips.mapper.AnimalTypeMapper;
import me.kiryakov.animal_chips.repository.AnimalTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AnimalTypeService {

    @Autowired
    private AnimalTypeRepository animalTypeRepository;
    @Autowired
    private AnimalTypeMapper animalTypeMapper;

    public AnimalTypeDTO create(AnimalTypeDTO animalTypeDTO) {
        boolean b = animalTypeRepository.existsByType(animalTypeDTO.getType());
        if (b) {
            throw new InaccessibleEntityException("This animal type already exists " + animalTypeDTO.getType());
        }
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
        boolean b = animalTypeRepository.existsByType(animalTypeDTO.getType());
        if (b) {
            throw new InaccessibleEntityException("This animal type already exists " + animalTypeDTO.getType());
        }
        AnimalType animalType = animalTypeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No animal type found with id " + id));

        animalType.setType(animalTypeDTO.getType());
        AnimalType save = animalTypeRepository.save(animalType);
        return animalTypeMapper.toDTO(save);


    }

    public void deleteAnimalType(Long id) {
        AnimalType animalType = animalTypeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No animal type found with id " + id));
        if (!animalType.getAnimals().isEmpty()){
            throw new DataConflictException("");
        }
        animalTypeRepository.delete(animalType);
    }
}
