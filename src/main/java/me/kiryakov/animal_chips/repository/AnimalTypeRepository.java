package me.kiryakov.animal_chips.repository;

import me.kiryakov.animal_chips.domain.AnimalType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimalTypeRepository extends JpaRepository<AnimalType, Long> {

}
