package com.oskarro.muzikum.model;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface GenericDao<T extends Serializable, K> extends Operations<T, K> {


}
