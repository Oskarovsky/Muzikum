package com.oskarro.muzikum.provider;

import com.oskarro.muzikum.provider.contractor.NuteczkiService;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(value = "/providers")
public class ProviderController {

    ProviderService providerService;
    NuteczkiService nuteczkiService;
    ProviderRepository providerRepository;

    public ProviderController(ProviderService providerService, NuteczkiService nuteczkiService, ProviderRepository providerRepository) {
        this.providerService = providerService;
        this.nuteczkiService = nuteczkiService;
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

    @GetMapping(value = "/{id}/nuteczki")
    String getNuteczkiTracklist(@PathVariable Integer id) {
        Optional<Provider> foundProvider = providerRepository.findById(id);
        return foundProvider.map(provider -> nuteczkiService.getTrackList(provider)).toString();
    }
}
