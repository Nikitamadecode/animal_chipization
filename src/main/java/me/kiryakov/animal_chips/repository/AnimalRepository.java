package me.kiryakov.animal_chips.repository;

import me.kiryakov.animal_chips.domain.Animal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalRepository extends JpaRepository<Animal, Long> {
}
