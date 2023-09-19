package com.challenge.api.service;

import com.challenge.api.model.Film;
import com.challenge.api.repository.FilmRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilmService {

    private final FilmRepository filmRepository;

    public FilmService(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    public void saveAll(List<Film> films) {
        this.filmRepository.saveAll(films);
    }

    public Film findById(Integer id){
        return this.filmRepository.findByEpisodeId(id);
    }


}
