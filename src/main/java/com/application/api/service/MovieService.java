package com.application.api.service;


import com.application.api.model.FavoritesMovie;
import com.application.api.model.Movie;
import org.springframework.security.core.Authentication;

import java.util.List;


public interface MovieService {
    Movie saveMovieToDb(Movie movie);

    List<Movie> getAllMovies(Pagable pageable);

    void addMovieToFavorites(Authentication authentication, Movie movie);

    void removeMovieFromFavorites(Authentication authentication,Movie movie);

    List<Movie> notInFavoritesMoviesInfo(Authentication authentication,Pagable pagable);

    List<FavoritesMovie> favoritesMoviesInfo(Authentication authentication);

    Movie findMovieById(Long moveId);
    Movie findMovieByTitle(String title);
}