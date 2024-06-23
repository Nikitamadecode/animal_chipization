package me.kiryakov.animal_chips.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaQuery;
import lombok.extern.slf4j.Slf4j;
import me.kiryakov.animal_chips.domain.*;
import me.kiryakov.animal_chips.dto.AnimalDTO;
import me.kiryakov.animal_chips.dto.AnimalSearchDTO;
import me.kiryakov.animal_chips.dto.AnimalTypeDTO;
import me.kiryakov.animal_chips.dto.EditAnimalTypeRequestDTO;
import me.kiryakov.animal_chips.exception.DataConflictException;
import me.kiryakov.animal_chips.exception.InaccessibleEntityException;
import me.kiryakov.animal_chips.exception.NotFoundException;
import me.kiryakov.animal_chips.mapper.AnimalMapper;
import me.kiryakov.animal_chips.repository.*;
import me.kiryakov.animal_chips.util.CriteriaManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
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
        List<Long> animalTypes = dto.getAnimalTypes();
        if (animalTypes.isEmpty()) {
            throw new DataConflictException("");
        }
        Animal animal = new Animal();
        animal.setWeight(dto.getWeight());
        animal.setHeight(dto.getHeight());
        animal.setLength(dto.getLength());
        animal.setGender(dto.getGender());
        animal.setLifeStatus(LifeStatus.ALIVE);
        animal.setChippingDateTime(LocalDateTime.now());
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
        if(dto.getLifeStatus().equals(LifeStatus.DEAD)) {
            animal.setDeathDateTime(LocalDateTime.now());
        }
        animal.setChippingDateTime(dto.getChippingDateTime());

        Account chipperAccount = accountRepository.findById(dto.getChipperId())
                .orElseThrow(() -> new NotFoundException("account with id" + dto.getChipperId() + " not found"));
        animal.setChipper(chipperAccount);

        Location chipperLocation = locationRepository.findById(dto.getChippingLocationId())
                .orElseThrow(() -> new NotFoundException("location with id" + dto.getChippingLocationId() + " not found"));

        List<VisitedLocation> visitedLocation = animal.getVisitedLocations().stream()
                .sorted(Comparator.comparing(VisitedLocation::getDateTime))
                .toList();

        if (!animal.getVisitedLocations().isEmpty()) {
            if (visitedLocation.getFirst().getLocation().equals(chipperLocation)) {
                throw new DataConflictException("alo bleat ti che ahuel");
            }
        }
        animal.setChippingLocation(chipperLocation);


        animalRepository.save(animal);

        return animalMapper.toDTO(animal);
    }

    public void delete(Long id) {
        Animal animal = animalRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("animal type with id" + id + " not found"));
        if (animal.getVisitedLocations().size() > 0) {
            throw new DataConflictException("ne lez suda pls");
        }
        log.info("deleting animal with id" + animal.getId());
        animalRepository.delete(animal);
    }

    public List<AnimalDTO> search(AnimalSearchDTO dto) {


        CriteriaQuery<Animal> criteria = criteriaManager.buildCriteriaForAnimal(dto, null, null);
        List<Animal> animals = entityRepository.searchByCriteria(criteria, dto.getFrom(), dto.getSize());

        return animals.stream()
                .map(animal -> animalMapper.toDTO(animal))
                .collect(Collectors.toList());

    }

    public AnimalDTO addAnimalType(Long animalId, Long animalTypeId) {
        Animal animal = animalRepository.findById(animalId)
                .orElseThrow(() -> new NotFoundException("animal type with id" + animalId + " not found"));
        AnimalType animalType = animalTypeRepository.findById(animalTypeId)
                .orElseThrow(() -> new NotFoundException("animal type with id" + animalTypeId + " not found"));
        boolean exist = animal.getAnimalTypes().contains(animalType);
        if (exist) {
            throw new InaccessibleEntityException("animal already have type" + animalTypeId);
        }
        animal.getAnimalTypes().add(animalType);
        animalRepository.save(animal);
        return animalMapper.toDTO(animal);
    }

    public AnimalDTO deleteAnimalType(Long animalId, Long animalTypeId) {
        Animal animal = animalRepository.findById(animalId)
                .orElseThrow(() -> new NotFoundException("animal with id" + animalId + " not found"));
        AnimalType animalType = animalTypeRepository.findById(animalTypeId)
                .orElseThrow(() -> new NotFoundException("animal type with id" + animalTypeId + " not found"));
        boolean exist = animal.getAnimalTypes().contains(animalType);
        if (!exist) {
            throw new NotFoundException(animalTypeId + " not exist");
        }
        if (animal.getAnimalTypes().size() == 1) {
            throw new DataConflictException("animal must have at least one type");
        }
        animal.getAnimalTypes().remove(animalType);
        animalRepository.save(animal);
        return animalMapper.toDTO(animal);
    }

    public AnimalDTO editAnimalType(Long animalId, EditAnimalTypeRequestDTO dto) {
        Animal animal = animalRepository.findById(animalId)
                .orElseThrow(() -> new NotFoundException("animal with id" + animalId + " not found"));
        AnimalType oldType = animalTypeRepository.findById(dto.getOldTypeId())
                .orElseThrow(() -> new NotFoundException("old type with id" + dto.getOldTypeId() + " not found"));
        AnimalType newType = animalTypeRepository.findById(dto.getNewTypeId())
                .orElseThrow(() -> new NotFoundException("new type with id" + dto.getNewTypeId() + " not found"));
        if (!animal.getAnimalTypes().contains(oldType)) {
            throw new NotFoundException("animal dont have this type");
        }
        if (animal.getAnimalTypes().contains(newType)) {
            throw new InaccessibleEntityException("animal already have this type");
        }
        animal.getAnimalTypes().remove(oldType);
        animal.getAnimalTypes().add(newType);
        animalRepository.save(animal);
        return animalMapper.toDTO(animal);
    }
}
