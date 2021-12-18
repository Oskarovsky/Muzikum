package com.oskarro.muzikum.provider;

import com.oskarro.muzikum.track.model.Genre;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class ProviderServiceImpl implements ProviderService {

    ProviderRepository providerRepository;

    public ProviderServiceImpl(ProviderRepository providerRepository) {
        this.providerRepository = providerRepository;
    }

    @Override
    public List<Provider> findAll() {
        return providerRepository.findAll();
    }

    @Override
    public Optional<Provider> findById(Integer id) {
        return providerRepository.findById(id);
    }

    @Override
    public Provider save(Provider provider) {
        return providerRepository.save(provider);
    }

    @Override
    public Optional<Provider> findByName(String name) {
        return providerRepository.findByName(name);
    }

    @Override
    public Collection<Genre> getAllGenresFromProvider(Integer id) {
        return findById(id)
                .map(Provider::getGenres)
                .orElse(null);
    }

}
