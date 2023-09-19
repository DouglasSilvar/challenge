package com.challenge.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public abstract class PageResponseDTO {

    private int count;
    private String next;
    private String previous;

}
