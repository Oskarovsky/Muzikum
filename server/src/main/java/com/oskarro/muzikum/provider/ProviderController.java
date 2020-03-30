package com.oskarro.muzikum.provider;

import com.oskarro.muzikum.provider.contractor.NuteczkiService;
import com.oskarro.muzikum.track.Track;
import com.oskarro.muzikum.track.TrackService;
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
    TrackService trackService;

    public ProviderController(ProviderService providerService, NuteczkiService nuteczkiService,
                              ProviderRepository providerRepository, TrackService trackService) {
        this.providerService = providerService;
        this.nuteczkiService = nuteczkiService;
        this.providerRepository = providerRepository;
        this.trackService = trackService;
    }

    @GetMapping(value = "/findAll")
    List<Provider> findAll() {
        return providerService.findAll();
    }

    @GetMapping(value = "/{provider_id}/tracks")
    @CrossOrigin(origins = "http://localhost:4200")
    List<Track> getTracksFromProvider(@PathVariable Integer provider_id) {
        return trackService.findByProviderId(provider_id);
    }

    @GetMapping(value = "/{provider_id}/{genre}")
    List<Track> getTracksFromProviderByGenre(@PathVariable Integer provider_id,
                                             @PathVariable String genre) {
        return trackService.findByProviderIdAndGenre(provider_id, genre.toUpperCase());
    }

    @PostMapping(value = "/add")
    public void addProvider(@RequestBody Provider provider) {
        providerService.save(provider);
    }
}
