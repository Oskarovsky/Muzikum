package com.oskarro.muzikum.provider;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProviderServiceImpl implements ProviderService {

    ProviderRepository providerRepository;

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
}
