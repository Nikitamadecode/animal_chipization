package me.kiryakov.animal_chips.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
}
