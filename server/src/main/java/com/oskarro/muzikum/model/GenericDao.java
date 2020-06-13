package com.oskarro.muzikum.model;

import java.util.List;

public interface GenericDao <E, K> {

    void save(E entity);
    void update(E entity);
    void delete(E entity);
    void deleteById(K key);
    E findOne(K key);
    List<E> findAll();


}
