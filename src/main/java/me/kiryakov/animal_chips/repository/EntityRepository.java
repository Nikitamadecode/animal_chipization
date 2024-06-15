package me.kiryakov.animal_chips.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EntityRepository {
    @Autowired
    private EntityManager em;

    public <T> List<T> searchByCriteria(CriteriaQuery<T> criteria, Integer from, Integer size) {
        TypedQuery<T> query = em.createQuery(criteria);
        query.setFirstResult(from);
        query.setMaxResults(size);
        return query.getResultList();
    }
}
