package me.kiryakov.animal_chips.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaQuery;
import me.kiryakov.animal_chips.domain.Account;
import me.kiryakov.animal_chips.domain.Animal;
import me.kiryakov.animal_chips.domain.AnimalType;
import me.kiryakov.animal_chips.domain.Location;
import me.kiryakov.animal_chips.dto.AnimalDTO;
import me.kiryakov.animal_chips.dto.AnimalSearchDTO;
import me.kiryakov.animal_chips.exception.NotFoundException;
import me.kiryakov.animal_chips.mapper.AnimalMapper;
import me.kiryakov.animal_chips.repository.*;
import me.kiryakov.animal_chips.util.CriteriaManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AnimalService {

    @Autowired
    private AnimalRepository animalRepository;
    @Autowired
    private LocationService locationService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private AnimalTypeRepository animalTypeRepository;
    @Autowired
    private AnimalMapper animalMapper;
    @Autowired
    private CriteriaManager criteriaManager;
    @Autowired
    private EntityRepository entityRepository;

    public AnimalDTO create(AnimalDTO dto) {
        Animal animal = new Animal();
        animal.setWeight(dto.getWeight());
        animal.setHeight(dto.getHeight());
        animal.setLength(dto.getLength());
        animal.setGender(dto.getGender());
        animal.setLifeStatus(dto.getLifeStatus());
        animal.setChippingDateTime(dto.getChippingDateTime());
        animal.setDeathDateTime(dto.getDeathDateTime());

        Account chipperAccount = accountRepository.findById(dto.getChipperId())
                .orElseThrow(() -> new NotFoundException("account with id" + dto.getChipperId() + " not found"));
        animal.setChipper(chipperAccount);

        Location chipperLocation = locationRepository.findById(dto.getChippingLocationId())
                .orElseThrow(() -> new NotFoundException("location with id" + dto.getChippingLocationId() + " not found"));
        animal.setChippingLocation(chipperLocation);

        for (Long id : dto.getAnimalTypes()) {
            AnimalType animalType = animalTypeRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("animal type with id" + id + " not found"));
            animal.getAnimalTypes().add(animalType);
        }
        animalRepository.save(animal);

        return animalMapper.toDTO(animal);
    }

    public AnimalDTO getById(Long id) {
        return animalRepository.findById(id)
                .map(animal -> animalMapper.toDTO(animal))
                .orElseThrow(() -> new NotFoundException("animal with id" + id + " not found"));
    }

    public AnimalDTO update(Long id, AnimalDTO dto) {
        Animal animal = animalRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("animal type with id" + id + " not found"));
        animal.setWeight(dto.getWeight());
        animal.setHeight(dto.getHeight());
        animal.setLength(dto.getLength());
        animal.setGender(dto.getGender());
        animal.setLifeStatus(dto.getLifeStatus());
        animal.setChippingDateTime(dto.getChippingDateTime());
        animal.setDeathDateTime(dto.getDeathDateTime());

        Account chipperAccount = accountRepository.findById(dto.getChipperId())
                .orElseThrow(() -> new NotFoundException("account with id" + dto.getChipperId() + " not found"));
        animal.setChipper(chipperAccount);

        Location chipperLocation = locationRepository.findById(dto.getChippingLocationId())
                .orElseThrow(() -> new NotFoundException("location with id" + dto.getChippingLocationId() + " not found"));
        animal.setChippingLocation(chipperLocation);

        for (Long typeId : dto.getAnimalTypes()) {
            AnimalType animalType = animalTypeRepository.findById(typeId)
                    .orElseThrow(() -> new NotFoundException("animal type with id" + typeId + " not found"));
            animal.getAnimalTypes().add(animalType);
        }
        animalRepository.save(animal);

        return animalMapper.toDTO(animal);
    }

    public void delete(Long id) {
        Animal animal = animalRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("animal type with id" + id + " not found"));
        animalRepository.delete(animal);
    }

    public List<AnimalDTO> search(AnimalSearchDTO dto) {
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("startDateTime", dto.getStartDateTime());
        paramsMap.put("endDateTime", dto.getEndDateTime());
        paramsMap.put("gender", dto.getGender());
        paramsMap.put("lifeStatus", dto.getLifeStatus());
        paramsMap.put("chippingLocationId", dto.getChippingLocationId());
        paramsMap.put("chipperId", dto.getChipperId());

        CriteriaQuery<Animal> criteria = criteriaManager.buildCriteria(paramsMap, Animal.class);
        List<Animal> animals = entityRepository.searchByCriteria(criteria, dto.getFrom(), dto.getSize());

        return animals.stream()
                .map(animal -> animalMapper.toDTO(animal))
                .collect(Collectors.toList());

    }

}
