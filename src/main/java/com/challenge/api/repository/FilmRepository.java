package com.challenge.api.repository;

import com.challenge.api.model.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmRepository extends JpaRepository<Film, Integer> {

    Film findByEpisodeId(Integer id);
}
