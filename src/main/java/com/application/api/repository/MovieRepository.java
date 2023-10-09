package com.application.api.repository;

import com.application.api.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    Movie findMovieById(Long movieId);
    Movie findMovieByTitle(String title);
}