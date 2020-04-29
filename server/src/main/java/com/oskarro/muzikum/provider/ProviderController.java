package com.oskarro.muzikum.provider;

import com.oskarro.muzikum.provider.contractor.NuteczkiService;
import com.oskarro.muzikum.track.Genre;
import com.oskarro.muzikum.track.Track;
import com.oskarro.muzikum.track.TrackService;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(value = "/api/providers")
@CrossOrigin(origins = "*", allowedHeaders = "*")
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

    @GetMapping(value = "/{provider_id}")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    Optional<Provider> getProviderById(@PathVariable Integer provider_id) {
        return providerService.findById(provider_id);
    }

    @GetMapping(value = "/findAll")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    List<Provider> findAll() {
        return providerService.findAll();
    }

    @GetMapping(value = "/{provider_id}/tracks")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    List<Track> getTracksFromProvider(@PathVariable Integer provider_id) {
        return trackService.findByProviderId(provider_id);
    }

    @GetMapping(value = "/{providerName}/all-tracks")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    List<Track> getTracksFromProvider(@PathVariable String providerName) {
        return trackService.findTracksByProviderName(providerName);
    }

    @GetMapping(value = "/{provider_id}/{genre}")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    List<Track> getTracksFromProviderByGenre(@PathVariable Integer provider_id,
                                             @PathVariable String genre) {
        return trackService.findByProviderIdAndGenre(provider_id, genre.toUpperCase());
    }

    @PostMapping(value = "/add")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public void addProvider(@RequestBody Provider provider) {
        providerService.save(provider);
    }

    @GetMapping(value = "/{provider_id}/genres")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    Collection<Genre> getAllGenresFromProvider(@PathVariable Integer provider_id) {
        return providerService.getAllGenresFromProvider(provider_id);
    }
}
