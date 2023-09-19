package com.challenge.api.controller;

import com.challenge.api.dto.PlanetDTO;
import com.challenge.api.service.PlanetService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/planets")
public class PlanetController {

    private final PlanetService service;

    public PlanetController(PlanetService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<PlanetDTO>> getAllPosts(@PageableDefault(size = 10, page = 0) Pageable pageable) throws IOException, URISyntaxException, InterruptedException {
        return ResponseEntity.ok(service.getPaged(pageable));
    }
}
