package com.challenge.api.service;

import com.challenge.api.client.SwapiClient;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.stream.Collectors;

@Service
public class DatabaseInitializerService {

    private final FilmService filmService;
    private final PlanetService planetService;
    private final SwapiClient swapiClient;

    public DatabaseInitializerService(FilmService filmService, PlanetService planetService, SwapiClient swapiClient) {
        this.filmService = filmService;
        this.planetService = planetService;
        this.swapiClient = swapiClient;
    }

    @PostConstruct
    public void init() throws IOException, InterruptedException, URISyntaxException {
        var filmResponse = swapiClient.fetchFilms(1);
        if (!CollectionUtils.isEmpty(filmResponse.getResults())){
            var listEntity = filmResponse.getResults()
                    .stream()
                    .map(filmDTO -> filmDTO.toEntity(filmDTO))
                    .collect(Collectors.toList());
            this.filmService.saveAll(listEntity);
        }
        var PlanetResponse = swapiClient.fetchPlanets(1);
        if (!CollectionUtils.isEmpty(PlanetResponse.getResults())){
            var listEntity = PlanetResponse.getResults()
                    .stream()
                    .map(PlanetDTO -> PlanetDTO.toEntity(PlanetDTO))
                    .collect(Collectors.toList());
            this.planetService.saveAll(listEntity);
        }
    }

}
