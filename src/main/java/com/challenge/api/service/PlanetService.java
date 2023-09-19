package com.challenge.api.service;

import com.challenge.api.client.SwapiClient;
import com.challenge.api.dto.FilmDTO;
import com.challenge.api.dto.PlanetDTO;
import com.challenge.api.model.Planet;
import com.challenge.api.repository.PlanetRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlanetService {

    private final PlanetRepository planetRepository;
    private final FilmService filmService;
    private final SwapiClient swapiClient;

    public PlanetService(PlanetRepository planetRepository, FilmService filmService, SwapiClient swapiClient) {
        this.planetRepository = planetRepository;
        this.filmService = filmService;
        this.swapiClient = swapiClient;
    }

    public Page<PlanetDTO> getPaged(Pageable pageable) throws IOException, URISyntaxException, InterruptedException {
        Page<PlanetDTO> dtoPage = planetRepository.findAll(pageable).map(planet -> new PlanetDTO().toDto(planet));

        if (dtoPage.isEmpty()) {
            List<PlanetDTO> listDto = swapiClient.fetchPlanets(pageable.getPageNumber() + 1).getResults();
            var listRemodel = listDto.stream()
                    .map(dto -> {
                        PlanetDTO remodel = dto.toRemodel(dto);
                        remodel.setFilmList(this.findList(remodel.getFilmList()));
                        return remodel;
                    })
                    .collect(Collectors.toList());

            return new PageImpl<>(listRemodel,pageable, listRemodel.size());
        } else {
            return dtoPage;
        }
    }

    public void saveAll(List<Planet> Planets) {
        planetRepository.saveAll(Planets);
    }

    private List<FilmDTO> findList(List<FilmDTO> filmDTOList){
        List<FilmDTO> newList = new ArrayList<>();
        filmDTOList.forEach( filmDTO -> {
            var film = this.filmService.findById(filmDTO.getEpisodeId());
            newList.add(new FilmDTO().toDto(film));
        });
        return newList;
    }

}
