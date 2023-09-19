package com.challenge.api.dto;

import com.challenge.api.model.Film;
import com.challenge.api.model.Planet;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PlanetDTO {

    private String name;
    @JsonProperty("rotation_period")
    private String rotationPeriod;
    @JsonProperty("orbital_period")
    private String orbitalPeriod;
    private String diameter;
    private String climate;
    private String gravity;
    private String terrain;
    @JsonProperty("surface_water")
    private String surfaceWater;
    private String population;
    private List<String> residents;
    private List<String> films;
    private List<FilmDTO> filmList;
    private Integer filmsQuantity;
    private String created;
    private String edited;
    private String url;

    public Planet toEntity(PlanetDTO dto){
        var filmListStr = dto.getFilms();
        List<Film> filmListEntity = new ArrayList<>();
        filmListStr.forEach( str -> {
            String[] parts = str.split("/");
            String lastElement = parts[parts.length - 1];
            filmListEntity.add(Film.builder().episodeId(Integer.parseInt(lastElement)).build());
        });
        return Planet.builder()
                .name(dto.getName())
                .climate(dto.getClimate())
                .terrain(dto.getTerrain())
                .films(filmListEntity)
                .build();
    }

    public PlanetDTO toRemodel(PlanetDTO dto){
        var filmListStr = dto.getFilms();
        List<FilmDTO> filmDTOList = new ArrayList<>();
        filmListStr.forEach( str -> {
            String[] parts = str.split("/");
            String lastElement = parts[parts.length - 1];
            filmDTOList.add(FilmDTO.builder()
                    .episodeId(Integer.parseInt(lastElement)).build());
        });
        return PlanetDTO.builder()
                .name(dto.getName())
                .climate(dto.getClimate())
                .terrain(dto.getTerrain())
                .filmsQuantity(filmDTOList.size())
                .filmList(filmDTOList)
                .build();
    }

    public PlanetDTO toDto(Planet entity){
        List<FilmDTO> filmDTOList = new ArrayList<>();
        entity.getFilms().forEach( film -> {
            filmDTOList.add(FilmDTO.builder()
                    .episodeId(film.getEpisodeId())
                    .director(film.getDirector())
                    .producer(film.getProducer())
                    .title(film.getTitle())
                    .releaseDate(film.getReleaseDate())
                    .build());
        });
        return PlanetDTO.builder()
                .name(entity.getName())
                .climate(entity.getClimate())
                .terrain(entity.getTerrain())
                .filmsQuantity(filmDTOList.size())
                .filmList(filmDTOList)
                .build();
    }
}
