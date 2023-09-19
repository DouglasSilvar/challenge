package com.challenge.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "film")
public class Film {

    @Id
    @Column(name = "episode_id", unique = true, nullable = false)
    @JsonProperty("episode_id")
    private int episodeId;

    @Column(name = "title")
    private String title;

    @Column(name = "director")
    private String director;

    @Column(name = "producer")
    private String producer;

    @Column(name = "release_date")
    @JsonProperty("release_date")
    private String releaseDate;

}
