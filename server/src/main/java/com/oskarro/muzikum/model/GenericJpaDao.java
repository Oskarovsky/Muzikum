package com.oskarro.muzikum.model;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class GenericJpaDao<T extends Serializable, K> extends AbstractJpaDao<T, K> implements GenericDao<T, K>{

    @Override
    public T create(T entity) {
        return null;
    }
}
