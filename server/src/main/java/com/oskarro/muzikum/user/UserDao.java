package com.oskarro.muzikum.user;

import java.util.List;

public interface UserDao {

    User findOne(Integer id);

    List<User> findAll();

    void save(User entity);

    void update(User entity);

    void delete(User entity);

    void deleteById(Integer entityId);
}
