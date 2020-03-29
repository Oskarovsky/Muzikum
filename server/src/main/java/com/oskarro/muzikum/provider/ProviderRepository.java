package com.oskarro.muzikum.provider;

import com.oskarro.muzikum.track.Genre;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProviderRepository extends CrudRepository<Provider, Integer> {

    List<Provider> findAll();

    Optional<Provider> findById(Integer id);

    Provider save(Provider provider);

    Optional<Provider> findByName(String name);

}
