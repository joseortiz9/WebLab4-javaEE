package ru.itmo.lab4.repositories;

import ru.itmo.lab4.models.PointEntity;
import ru.itmo.lab4.models.UserEntity;

import javax.persistence.TypedQuery;
import java.util.List;

public class PointsRepository extends GenericRepositoryJPA<PointEntity> {

    public List<PointEntity> findAllByUser(UserEntity owner) {
        TypedQuery<PointEntity> query = em.createNamedQuery(PointEntity.FIND_ALL_BY_USER, PointEntity.class);
        query.setParameter("user", owner);
        return query.getResultList();
    }
}
