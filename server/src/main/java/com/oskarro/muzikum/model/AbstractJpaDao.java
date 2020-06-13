package com.oskarro.muzikum.model;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;

public abstract class AbstractJpaDao<T extends Serializable, K> {

    private Class<T> clazz;

    @PersistenceContext
    EntityManager entityManager;

    public final void setClazz(final Class<T> clazzToSet) {
        this.clazz = clazzToSet;
    }

    public T findOne(final K entityId){
        return entityManager.find(clazz, entityId);
    }

    @SuppressWarnings("unchecked")
    public List<T> findAll() {
        return entityManager.createQuery("from " + clazz.getName()).getResultList();
    }

    public void save(final T entity){
        entityManager.persist(entity);
    }

    public void update(final T entity){
        entityManager.merge(entity);
    }

    public void delete(final T entity){
        entityManager.remove(entity);
    }
    public void deleteById(final K entityId){
        T entity = findOne(entityId);
        delete(entity);
    }


}
