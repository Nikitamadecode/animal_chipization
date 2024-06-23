package me.kiryakov.animal_chips.repository;

import me.kiryakov.animal_chips.domain.VisitedLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitedLocationRepository extends JpaRepository<VisitedLocation, Long> {

}
