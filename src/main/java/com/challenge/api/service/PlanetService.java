package com.challenge.api.service;

import com.challenge.api.client.SwapiClient;
import com.challenge.api.dto.FilmDTO;
import com.challenge.api.dto.PlanetDTO;
import com.challenge.api.dto.PlanetDTORequest;
import com.challenge.api.model.Film;
import com.challenge.api.model.Planet;
import com.challenge.api.repository.PlanetRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

    public Page<PlanetDTO> getPaged(Pageable pageable) {
        Page<PlanetDTO> dtoPage = this.planetRepository.findAll(pageable).map(planet -> new PlanetDTO().toDto(planet));

        if (dtoPage.isEmpty() || dtoPage.getContent().size() < pageable.getPageSize()) {
            var response = this.swapiClient.fetchPlanets(pageable.getPageNumber() + 1);
            if(response != null && !response.getResults().isEmpty() ) {
                var listRemodel = this.remodelList(response.getResults());
                if(dtoPage.getContent().size() < pageable.getPageSize()){
                    listRemodel.addAll(dtoPage.getContent());
                }
                return new PageImpl<>(listRemodel, pageable, listRemodel.size());
            }
        }
            return dtoPage;
    }

    public PlanetDTO searchPlanet(String planet){
        var planetPersisted = planetRepository.findByNameIgnoreCase(planet);

        if(planetPersisted == null){
            var response =  this.swapiClient.searchPlanet(planet);
            if(response != null && !response.getResults().isEmpty() ) {
                var listRemodel = this.remodelList(response.getResults());
                return listRemodel.get(0);
            }
                return PlanetDTO.builder()
                        .name("Planet not find")
                        .build();
        }
        return new PlanetDTO().toDto(planetPersisted);
    }

    public PlanetDTO createPlanet(PlanetDTORequest dto){
        var planetPersisted = planetRepository.findByNameIgnoreCase(dto.getName());
        if(planetPersisted != null){
            return PlanetDTO.builder()
                    .name("Planet alread exists")
                    .build();
        }
        List<Film> filmEntityList = new ArrayList<>();
        if(dto.getFilms() != null && !dto.getFilms().isEmpty()){
            dto.getFilms().forEach( film -> filmEntityList.add(Film.builder().episodeId(film.getEpisodeId()).build()));
        }
        var entity = Planet.builder()
                .name(dto.getName())
                .terrain(dto.getTerrain())
                .climate(dto.getClimate())
                .build();
        if(!filmEntityList.isEmpty()){
            entity.setFilms(filmEntityList);
        }
        var toPersist = planetRepository.save(entity);
        var resultDto = new PlanetDTO().toDto(toPersist);
        if(resultDto.getFilmList() != null && !resultDto.getFilmList().isEmpty()){
            var dtoFilms = this.findList(resultDto.getFilmList());
            resultDto.setFilmList(dtoFilms);
        }
        return resultDto;
    }

    public void saveAll(List<Planet> Planets) {
        this.planetRepository.saveAll(Planets);
    }

    private List<PlanetDTO> remodelList(List<PlanetDTO> planetDto){
        return planetDto.stream()
                .map(dto -> {
                    PlanetDTO remodel = dto.toRemodel(dto);
                    remodel.setFilmList(this.findList(remodel.getFilmList()));
                    return remodel;
                }).collect(Collectors.toList());
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
