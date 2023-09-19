package com.challenge.api.dto;

import com.challenge.api.model.Film;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class FilmDTO {

    @JsonProperty("episode_id")
    private int episodeId;
    private String title;
    @JsonProperty("opening_crawl")
    private String openingCrawl;
    private String director;
    private String producer;
    @JsonProperty("release_date")
    private String releaseDate;
    private List<String> characters;
    private List<String> planets;
    private List<String> starships;
    private List<String> vehicles;
    private List<String> species;
    private String created;
    private String edited;
    private String url;

    public Film toEntity(FilmDTO dto){
        return Film.builder()
                .episodeId(dto.getEpisodeId())
                .director(dto.getDirector())
                .producer(dto.getProducer())
                .title(dto.getTitle())
                .releaseDate(dto.getReleaseDate())
                .build();
    }

    public FilmDTO toDto(Film ent){
        return FilmDTO.builder()
                .episodeId(ent.getEpisodeId())
                .director(ent.getDirector())
                .producer(ent.getProducer())
                .title(ent.getTitle())
                .releaseDate(ent.getReleaseDate())
                .build();
    }

}
