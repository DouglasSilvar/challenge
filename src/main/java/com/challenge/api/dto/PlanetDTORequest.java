package com.challenge.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PlanetDTORequest {

    @NotBlank(message = "Name is mandatory")
    @Size(min = 3, message = "Name should have at least 5 characters")
    private String name;

    @NotBlank(message = "Climate is mandatory")
    @Size(min = 3, message = "Climate should have at least 5 characters")
    private String climate;

    @NotBlank(message = "Terrain is mandatory")
    @Size(min = 3, message = "Terrain should have at least 5 characters")
    private String terrain;
    private List<FilmDTORequest> films;

}
