package com.application.api.service;


import com.application.api.model.FavoritesMovie;
import com.application.api.model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import java.util.List;


public interface MovieService {
    Movie saveMovieToDb(Movie movie);

    Page<Movie> getAllMovies(Pageable pageable);

    void addMovieToFavorites(Authentication authentication, Movie movie);

    void removeMovieFromFavorites(Authentication authentication,Movie movie);

    List<Movie> notInFavoritesMoviesInfo(Authentication authentication,Pageable pagable);

    List<FavoritesMovie> favoritesMoviesInfo(Authentication authentication);

    Movie findMovieById(Long moveId);
    Movie findMovieByTitle(String title);
}