package com.oskarro.muzikum.model;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;

@SuppressWarnings("unchecked")
@Repository
public abstract class GenericDaoImpl<E, K extends Serializable> implements GenericDao<E, K> {

    private Class<E> clazz;

    @PersistenceContext
    EntityManager entityManager;

    protected Class<? extends E> daoType;

    public void setClazz(Class<E> clazzToSet) {
        this.clazz = clazzToSet;
    }

    @Override
    public void save(E entity) {
        entityManager.persist(entity);
    }

    @Override
    public void update(E entity) {
        entityManager.merge(entity);
    }

    @Override
    public void delete(E entity) {
        entityManager.remove(entity);
    }

    @Override
    public void deleteById(K entityId) {
        E entity = findOne(entityId);
        delete(entity);
    }

    @Override
    public E findOne(K key) {
        return entityManager.find(clazz, key);
    }

    @Override
    public List<E> findAll() {
        return entityManager.createQuery("from " + clazz.getName()).getResultList();
    }
}
