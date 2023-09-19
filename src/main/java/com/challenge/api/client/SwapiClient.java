package com.challenge.api.client;

import com.challenge.api.dto.FilmResponseDTO;
import com.challenge.api.dto.PlanetResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Slf4j
@Component
public class SwapiClient {

    @Value("${swapi.url}")
    private String swapiUrl;

    private static final String PLANET_ENDPOINT = "planets/?page=";
    private static final String PLANET_ENDPOINT_SEARCH = "planets/?search=";
    private static final String FILMS_ENDPOINT = "films/?page=";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public <T> T fetchData(String endpoint, Class<T> responseType, Integer page) {
        try {
            log.info("Initialize client on " + endpoint + page);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(swapiUrl+endpoint+page))
                    .GET()
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());

            return  objectMapper.readValue(response.body(), responseType);

        } catch (IOException | InterruptedException | URISyntaxException e) {
            log.error("Error to search or convert data: ", e);
            return null;
        }
    }

    public PlanetResponseDTO searchPlanet(String planet) {
        try {
            log.info("Initialize client on " + PLANET_ENDPOINT_SEARCH + planet);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(swapiUrl + PLANET_ENDPOINT_SEARCH + planet))
                    .GET()
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());

            return  objectMapper.readValue(response.body(), PlanetResponseDTO.class);

        } catch (IOException | InterruptedException | URISyntaxException e) {
            log.error("Error to search or convert data: ", e);
            return null;
        }
    }

    public PlanetResponseDTO fetchPlanets(Integer page) {
        return fetchData(PLANET_ENDPOINT, PlanetResponseDTO.class, page);
    }

    public FilmResponseDTO fetchFilms(Integer page ) {
        return fetchData(FILMS_ENDPOINT, FilmResponseDTO.class, page);
    }

}
