package com.challenge.api.service;

import com.challenge.api.client.SwapiClient;
import com.challenge.api.dto.FilmDTO;
import com.challenge.api.dto.FilmResponseDTO;
import com.challenge.api.dto.PlanetDTO;
import com.challenge.api.dto.PlanetResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.concurrent.Executor;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DatabaseInitializerServiceTest {

    @Mock
    private FilmService mockFilmService;
    @Mock
    private PlanetService mockPlanetService;
    @Mock
    private SwapiClient mockSwapiClient;
    @Mock
    private Executor mockCustomExecutor;

    private DatabaseInitializerService databaseInitializerServiceUnderTest;

    @BeforeEach
    void setUp() {
        databaseInitializerServiceUnderTest = new DatabaseInitializerService(mockFilmService, mockPlanetService,
                mockSwapiClient, mockCustomExecutor);
    }

    @Test
    void testInit() {

        doAnswer(invocation -> {
            ((Runnable) invocation.getArguments()[0]).run();
            return null;
        }).when(mockCustomExecutor).execute(any(Runnable.class));

        final FilmResponseDTO filmResponseDTO = new FilmResponseDTO(
                List.of(new FilmDTO()));
        when(mockSwapiClient.fetchFilms(1)).thenReturn(filmResponseDTO);

        final PlanetResponseDTO planetResponseDTO = new PlanetResponseDTO(
                List.of(new PlanetDTO()));
        when(mockSwapiClient.fetchPlanets(1)).thenReturn(planetResponseDTO);
        databaseInitializerServiceUnderTest.init();

        verify(mockSwapiClient).fetchFilms(1);
        verify(mockSwapiClient).fetchPlanets(1);

//        verify(mockFilmService,times(1)).saveAll(Arrays.asList(Film.builder().build()));
//        verify(mockPlanetService,times(1)).saveAll(Arrays.asList(Planet.builder().build()));
    }
}
