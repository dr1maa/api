package com.application.api.controllers;


import com.application.api.model.FavoritesMovie;
import com.application.api.model.Movie;
import com.application.api.model.User;
import com.application.api.service.MovieService;
import com.application.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api/movies")
public class MovieController {
    private final UserService userService;
    private final MovieService movieService;

    @Autowired
    public MovieController(UserService userService, MovieService movieService) {
        this.userService = userService;
        this.movieService = movieService;
    }

    @GetMapping("/savedMovies")
    public ResponseEntity<List<Movie>> getSavedMovies(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int perPage
    ) {
        Pageable pagable = PageRequest.of(1, 5);
        Page<Movie> saveMoviePage = (Page<Movie>) movieService.getAllMovies(pagable);
        return ResponseEntity.ok(saveMoviePage.getContent());
    }

    @PostMapping("/addToFavorites")
    public ResponseEntity<String> addToFavorites(Authentication authentication, @RequestBody Long movieId) {
        Movie movie = movieService.findMovieById(movieId);
        String username = authentication.getName();
        User user = userService.getUserByUsername(username);
        if (movie != null && user != null) {
            movieService.addMovieToFavorites(authentication, movie);
            return ResponseEntity.ok("Видео добавлено в избранное");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Сохранение не удалось, пользователь или видео отсутствует");
        }
    }

    @DeleteMapping("/deleteFromFavorites")
    public ResponseEntity<String> removeVideoFromFavorites(Authentication authentication, @RequestBody Long mobieId) {
        Movie movie = movieService.findMovieById(mobieId);
        String username = authentication.getName();
        User user = userService.getUserByUsername(username);
        if (movie == null || user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Фильм или пользователь не найден");
        }
        List<FavoritesMovie> existFavoriteMovies = movieService.favoritesMoviesInfo(authentication);
        if (existFavoriteMovies == null) {
            return ResponseEntity.badRequest().body("Фильм не найден в избранном");
        }
        movieService.removeMovieFromFavorites(authentication, movie);
        return ResponseEntity.ok("Фильм успешно удален из избранного");
    }

    @GetMapping("/notInFavoritesMovies")
    public ResponseEntity<List<Movie>> notInFavoritesMovies(
            Authentication authentication,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int perPage
    ) {
        String username = authentication.getName();
        User user = userService.getUserByUsername(username);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Pageable pagable = PageRequest.of(page, perPage);
        List<Movie> notInFavoritesMovie = movieService.notInFavoritesMoviesInfo(authentication, pagable);
        if (notInFavoritesMovie == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(notInFavoritesMovie);
    }

    @GetMapping("/favoritesMovies")
    public ResponseEntity<List<Movie>> favoritesMoviesList(Authentication authentication) {
        String username = authentication.getName();
        User user = userService.getUserByUsername(username);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        List<FavoritesMovie> favoritesMovies = movieService.favoritesMoviesInfo(authentication);
        List<Movie> favoritesMoviesList = favoritesMovies.stream()
                .map(FavoritesMovie::getMovie)
                .collect(Collectors.toList());
        return ResponseEntity.ok(favoritesMoviesList);
    }
}
