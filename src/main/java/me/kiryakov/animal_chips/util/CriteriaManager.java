package me.kiryakov.animal_chips.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import me.kiryakov.animal_chips.domain.Account;
import me.kiryakov.animal_chips.domain.Animal;
import me.kiryakov.animal_chips.domain.Location;
import me.kiryakov.animal_chips.domain.VisitedLocation;
import me.kiryakov.animal_chips.dto.AnimalSearchDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class CriteriaManager {
    @Autowired
    private EntityManager em;

    public <T> CriteriaQuery<T> buildCriteria(Map<String, Object> paramsMap, Class<T> tClass) {

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<T> query = criteriaBuilder.createQuery(tClass);
        Root<T> from = query.from(tClass);
        List<Predicate> predicates = new ArrayList<Predicate>();
        paramsMap.forEach((key, value) -> {
            if (value != null) {
                Predicate like = criteriaBuilder.like(criteriaBuilder.upper(from.get(key)), "%" + value.toString().toUpperCase() + "%");
                predicates.add(like);
            }
        });
        Predicate globalPredicate = criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        query.where(globalPredicate);
        query.orderBy(criteriaBuilder.asc(from.get("id")));
        return query;
    }

    public CriteriaQuery<VisitedLocation> buildCriteriaForVisitedLocation(LocalDateTime start, LocalDateTime end, Animal animal) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<VisitedLocation> query = criteriaBuilder.createQuery(VisitedLocation.class);
        Root<VisitedLocation> from = query.from(VisitedLocation.class);
        List<Predicate> predicates = new ArrayList<>();
        if (start != null) {
            Predicate startPred = criteriaBuilder.greaterThanOrEqualTo(from.get("dateTime"), start);
            predicates.add(startPred);
        }
        if (end != null) {
            Predicate endPred = criteriaBuilder.lessThanOrEqualTo(from.get("dateTime"), end);
            predicates.add(endPred);
        }
        Predicate animalPredicate = criteriaBuilder.equal(from.get("animal"), animal);
        predicates.add(animalPredicate);

        Predicate globalPredicate = criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        query.where(globalPredicate);
        query.orderBy(criteriaBuilder.asc(from.get("dateTime")));
        return query;
    }

    public CriteriaQuery<Animal> buildCriteriaForAnimal(AnimalSearchDTO dto, Account account, Location location) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Animal> query = criteriaBuilder.createQuery(Animal.class);
        Root<Animal> from = query.from(Animal.class);
        List<Predicate> predicates = new ArrayList<>();
        if (dto.getStartDateTime() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(from.get("chippingDateTime"), dto.getStartDateTime()));
        }
        if (dto.getEndDateTime() != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(from.get("chippingDateTime"), dto.getEndDateTime()));
        }
        if (dto.getLifeStatus() != null) {
            predicates.add(criteriaBuilder.equal(from.get("lifeStatus"), dto.getLifeStatus()));
        }
        if (dto.getGender() != null) {
            predicates.add(criteriaBuilder.equal(from.get("gender"), dto.getGender()));
        }
        if(dto.getChipperId() != null) {
            predicates.add(criteriaBuilder.equal(from.get("chipper").get("id"), dto.getChipperId()));
        }
        if(dto.getChippingLocationId() != null) {
            predicates.add(criteriaBuilder.equal(from.get("chippingLocation").get("id"), dto.getChippingLocationId()));
        }
        Predicate globalPredicate = criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        query.where(globalPredicate);
        return query;
    }
}