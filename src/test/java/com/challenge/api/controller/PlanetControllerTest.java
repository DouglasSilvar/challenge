package com.challenge.api.controller;

import com.challenge.api.dto.FilmDTO;
import com.challenge.api.dto.PlanetDTO;
import com.challenge.api.dto.PlanetDTORequest;
import com.challenge.api.service.PlanetService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PlanetController.class)
class PlanetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlanetService mockService;

    @Test
    void testGetAllPlanets() throws Exception {
        final Page<PlanetDTO> planetDTOS = new PageImpl<>(
                List.of(new PlanetDTO("name", "rotationPeriod", "orbitalPeriod", "diameter", "climate", "gravity",
                        "terrain", 0, "surfaceWater", "population", List.of("value"), List.of("value"),
                        List.of(new FilmDTO(0, "title", "openingCrawl", "director", "producer", "releaseDate",
                                List.of("value"), List.of("value"), List.of("value"), List.of("value"),
                                List.of("value"), "created", "edited", "url")), "created", "edited", "url")));
        when(mockService.getPaged(any(Pageable.class))).thenReturn(planetDTOS);

        final MockHttpServletResponse response = mockMvc.perform(get("/api/planets")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).hasSize(838);
    }

    @Test
    void testGetAllPlanets_PlanetServiceReturnsNoItems() throws Exception {
        when(mockService.getPaged(any(Pageable.class))).thenReturn(new PageImpl<>(Collections.emptyList()));
        final MockHttpServletResponse response = mockMvc.perform(get("/api/planets")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).hasSize(201);
    }

    @Test
    void testSearchPlanet() throws Exception {

        final PlanetDTO planetDTO = new PlanetDTO("name", "rotationPeriod", "orbitalPeriod", "diameter", "climate",
                "gravity", "terrain", 0, "surfaceWater", "population", List.of("value"), List.of("value"),
                List.of(new FilmDTO(0, "title", "openingCrawl", "director", "producer", "releaseDate", List.of("value"),
                        List.of("value"), List.of("value"), List.of("value"), List.of("value"), "created", "edited",
                        "url")), "created", "edited", "url");
        when(mockService.searchPlanet("planet")).thenReturn(planetDTO);
        final MockHttpServletResponse response = mockMvc.perform(get("/api/planets/{planet}", "planet")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).hasSize(636);
    }

    @Test
    void testCreatePlanet() throws Exception {
        final PlanetDTO planetDTO = new PlanetDTO("name", "rotationPeriod", "orbitalPeriod", "diameter", "climate",
                "gravity", "terrain", 0, "surfaceWater", "population", List.of("value"), List.of("value"),
                List.of(new FilmDTO(0, "title", "openingCrawl", "director", "producer", "releaseDate", List.of("value"),
                        List.of("value"), List.of("value"), List.of("value"), List.of("value"), "created", "edited",
                        "url")), "created", "edited", "url");
        when(mockService.createPlanet(any(PlanetDTORequest.class))).thenReturn(planetDTO);
        String json = new ObjectMapper().writeValueAsString(PlanetDTORequest.builder().climate("seco").name("ronaldo").terrain("arido").build());
        final MockHttpServletResponse response = mockMvc.perform(post("/api/planets")
                        .content(json).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).hasSize(636);
    }

    @Test
    void testDeletePlanet() throws Exception {
        final MockHttpServletResponse response = mockMvc.perform(delete("/api/planets/{planet}", "planet")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
        assertThat(response.getContentAsString()).isEmpty();
        verify(mockService).deletePlanet("planet");
    }
}
