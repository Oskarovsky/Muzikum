package com.oskarro.muzikum.provider;

import com.oskarro.muzikum.crawler.CrawlerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProviderServiceImpl implements ProviderService {

    ProviderRepository providerRepository;
    CrawlerService crawlerService;

    public ProviderServiceImpl(ProviderRepository providerRepository, CrawlerService crawlerService) {
        this.providerRepository = providerRepository;
        this.crawlerService = crawlerService;
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
    public String getCrawler(Integer id) {
        Optional<Provider> foundProvider = findById(id);
        return foundProvider
                .map(provider -> crawlerService.parseWeb(provider))
                .orElse(null);
    }

}
