package com.challenge.api.controller;

import com.challenge.api.dto.PlanetDTO;
import com.challenge.api.dto.PlanetDTORequest;
import com.challenge.api.service.PlanetService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/planets")
public class PlanetController {

    private final PlanetService service;

    public PlanetController(PlanetService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<PlanetDTO>> getAllPosts(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(service.getPaged(pageable));
    }

    @GetMapping(value = "/{planet}")
    public ResponseEntity<PlanetDTO> searchPlanet(@PathVariable String planet) {
        return ResponseEntity.ok(service.searchPlanet(planet));
    }

    @PostMapping
    public ResponseEntity<PlanetDTO> createPlanet(@Valid @RequestBody PlanetDTORequest planetDTO) {
        return ResponseEntity.ok(service.createPlanet(planetDTO));
    }
}
