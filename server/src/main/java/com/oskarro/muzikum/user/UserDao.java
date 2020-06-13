package com.oskarro.muzikum.user;

import com.oskarro.muzikum.model.GenericDao;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Component
public class UserDao implements GenericDao<User, Integer> {

    private final EntityManager entityManager;

    public UserDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void add(User entity) {

    }

    @Override
    public void saveOrUpdate(User entity) {

    }

    @Override
    public void update(User entity) {

    }

    @Override
    public void delete(User entity) {

    }

    @Override
    public void deleteById(Integer key) {

    }

    @Override
    public Optional<User> findOne(Integer key) {
        return Optional.ofNullable(entityManager.find(User.class, key));
    }

    @Override
    public List<User> findAll() {
        Query query = entityManager.createQuery("SELECT e FROM User e");
        return query.getResultList();
    }
}
