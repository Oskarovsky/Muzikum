package com.oskarro.muzikum.provider;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController(value = "/provider")
public class ProviderController {

    ProviderService providerService;
    ProviderRepository providerRepository;

    public ProviderController(ProviderService providerService, ProviderRepository providerRepository) {
        this.providerService = providerService;
        this.providerRepository = providerRepository;
    }

    @GetMapping(value = "/findAll")
    List<Provider> findAll() {
        return providerService.findAll();
    }

    @PostMapping(value = "/add")
    void addProvider(@RequestBody Provider provider) {
        providerService.save(provider);
    }
}
