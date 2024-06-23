package me.kiryakov.animal_chips.mapper;

import me.kiryakov.animal_chips.domain.AnimalType;
import me.kiryakov.animal_chips.dto.AnimalTypeDTO;
import org.springframework.stereotype.Component;

@Component
public class AnimalTypeMapper {
    public AnimalTypeDTO toDTO(AnimalType animalType) {
        AnimalTypeDTO animalTypeDTO = new AnimalTypeDTO();
        animalTypeDTO.setId(animalType.getId());
        animalTypeDTO.setType(animalType.getType());
        return animalTypeDTO;
    }
}
