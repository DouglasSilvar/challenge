package com.challenge.api.service;

import com.challenge.api.client.SwapiClient;
import com.challenge.api.model.Planet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DatabaseInitializerService {

    private final FilmService filmService;
    private final PlanetService planetService;
    private final SwapiClient swapiClient;
    private final Executor customExecutor;;

    public DatabaseInitializerService(FilmService filmService, PlanetService planetService, SwapiClient swapiClient, Executor customExecutor) {
        this.filmService = filmService;
        this.planetService = planetService;
        this.swapiClient = swapiClient;
        this.customExecutor = customExecutor;
    }

    @Async
    @PostConstruct
    public void init() {

        CompletableFuture<Void> filmFuture = CompletableFuture.runAsync(() -> {
            var filmResponse = swapiClient.fetchFilms(1);
            if (!CollectionUtils.isEmpty(filmResponse.getResults())) {
                var listEntity = filmResponse.getResults()
                        .stream()
                        .map(filmDTO -> filmDTO.toEntity(filmDTO))
                        .collect(Collectors.toList());
                log.info("Persisting all films");
                this.filmService.saveAll(listEntity);
            }
        }, customExecutor);

        CompletableFuture<List<Planet>> planetDataFuture = CompletableFuture.supplyAsync(() -> {
            var planetResponse = swapiClient.fetchPlanets(1);
            if (!CollectionUtils.isEmpty(planetResponse.getResults())) {
                return planetResponse.getResults()
                        .stream()
                        .map(planetDTO -> planetDTO.toEntity(planetDTO))
                        .collect(Collectors.toList());
            }
            return Collections.emptyList();
        }, customExecutor);

        CompletableFuture.allOf(filmFuture, planetDataFuture)
                .thenRun(() -> {
                    List<Planet> planetEntities = planetDataFuture.join();
                    log.info("Persisting all planets");
                    this.planetService.saveAll(planetEntities);
                });
    }

}
