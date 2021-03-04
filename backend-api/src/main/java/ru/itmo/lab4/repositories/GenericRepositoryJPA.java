package ru.itmo.lab4.repositories;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class GenericRepositoryJPA<T> implements GenericRepository<T> {

    @PersistenceContext(unitName = "appManagement")
    protected EntityManager em;
    @Inject
    private UserTransaction userTransaction;
    private final Class<T> type;

    public GenericRepositoryJPA() {
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        type = (Class) pt.getActualTypeArguments()[0];
    }

    public T create(final T t) throws Exception {
        userTransaction.begin();
        em.persist(t);
        userTransaction.commit();
        return t;
    }

    public void delete(final Object object) {
        em.remove(em.merge(object));
    }

    public T find(final Object id) {
        return (T) em.find(type, id);
    }

    public T update(final T t) {
        return em.merge(t);
    }

    public Iterable<T> findAll() {
        CriteriaBuilder cb = this.em.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = cb.createQuery(type);
        Root<T> root = criteriaQuery.from(type);
        criteriaQuery.select(root);
        TypedQuery<T> query = em.createQuery(criteriaQuery);
        return query.getResultList();
    }
}
