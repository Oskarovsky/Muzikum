package com.oskarro.muzikum.model;

import java.io.Serializable;
import java.util.List;

public interface Operations<T extends Serializable, K> {

    T findOne(final K id);

    List<T> findAll();

    T create(final T entity);

    void update(final T entity);

    void delete(final T entity);

    void deleteById(final K entityId);
}
