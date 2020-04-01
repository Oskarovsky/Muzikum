package com.oskarro.muzikum.provider;

import com.oskarro.muzikum.track.Genre;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ProviderService {

    List<Provider> findAll();

    Optional<Provider> findById(Integer id);

    Provider save(Provider provider);

    Optional<Provider> findByName(String name);

    String getCrawler(Integer id);

    Collection<Genre> getAllGenresFromProvider(Integer id);
}
