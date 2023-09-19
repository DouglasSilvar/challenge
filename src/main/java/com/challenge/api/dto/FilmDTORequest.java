package com.challenge.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class FilmDTORequest {

    @JsonProperty("episode_id")
    @Min(value = 1, message = "Episode ID should be between 1 and 6")
    @Max(value = 6, message = "Episode ID should be between 1 and 6")
    private int episodeId;

}
