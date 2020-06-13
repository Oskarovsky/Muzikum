package com.oskarro.muzikum.model;

import java.util.List;
import java.util.Optional;

public interface GenericDao <E, K> {

    void add(E entity) ;
    void saveOrUpdate(E entity);
    void update(E entity);
    void delete(E entity);
    void deleteById(K key);
    Optional<E> findOne(K key);
    List<E> findAll();


}
