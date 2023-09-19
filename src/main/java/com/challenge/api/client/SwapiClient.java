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

    private static final String PLANET_ENDPOINT = "planets/?";
    private static final String FILMS_ENDPOINT = "films/?";
    private final ObjectMapper objectMapper = new ObjectMapper();

    private <T> T fetchData(String endpoint, Class<T> responseType, Integer page, String query) {
        try {
            String finalEndpoint = (query != null && !query.isEmpty()) ? endpoint + "search=" + query : endpoint + "page=" + page;
            log.info("Initialize client on " + finalEndpoint);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(swapiUrl + finalEndpoint))
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

    public PlanetResponseDTO fetchPlanets(Integer page) {
        return fetchData(PLANET_ENDPOINT, PlanetResponseDTO.class, page, null);
    }

    public PlanetResponseDTO searchPlanet(String planet) {
        return fetchData(PLANET_ENDPOINT, PlanetResponseDTO.class, null, planet);
    }

    public FilmResponseDTO fetchFilms(Integer page ) {
        return fetchData(FILMS_ENDPOINT, FilmResponseDTO.class, page, null);
    }

}
