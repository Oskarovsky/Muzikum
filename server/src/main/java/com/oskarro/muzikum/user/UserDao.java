package com.oskarro.muzikum.user;

import com.oskarro.muzikum.model.GenericDao;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public interface UserDao {

    User findOne(Integer id);

    List<User> findAll();

    void save(User entity);

    void update(User entity);

    void delete(User entity);

    void deleteById(Integer entityId);
}
