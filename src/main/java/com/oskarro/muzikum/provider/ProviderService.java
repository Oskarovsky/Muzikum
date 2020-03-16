package com.oskarro.muzikum.provider;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface ProviderService {

    List<Provider> findAll();

    Optional<Provider> findById(Integer id);

    Provider save(Provider provider);

    Optional<Provider> findByName(String name);
}
