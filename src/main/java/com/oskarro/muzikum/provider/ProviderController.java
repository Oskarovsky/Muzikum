package com.oskarro.muzikum.provider;

import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping(value = "/providers")
public class ProviderController {

    ProviderService providerService;
    ProviderRepository providerRepository;

    public ProviderController(ProviderService providerService, ProviderRepository providerRepository) {
        this.providerService = providerService;
        this.providerRepository = providerRepository;
    }

    @GetMapping(value = "init")
    public String initProviders() {
        providerRepository.saveAll(Arrays.asList(
                Provider.builder().id(1).description("nice").url("https://nuteczki.eu/").name("nuteczki").build()
        ));
        return "All providers has been added to temporary database";
    }

    @GetMapping(value = "/findAll")
    List<Provider> findAll() {
        return providerService.findAll();
    }

    @PostMapping(value = "/add")
    void addProvider(@PathVariable Provider provider) {
        providerService.save(provider);
    }

    @GetMapping(value = "/{id}/getCrawler")
    String getCrawler(@PathVariable String id) {
        return providerService.getCrawler(Integer.valueOf(id));
    }
}
