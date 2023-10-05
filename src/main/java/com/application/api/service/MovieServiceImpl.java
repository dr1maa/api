package com.application.api.service;

import com.application.api.model.FavoritesMovie;
import com.application.api.model.Movie;
import com.application.api.model.User;
import com.application.api.repository.FavoriteMovieRepository;
import com.application.api.repository.MovieRepository;
import com.application.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {
    @Autowired
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;
    private final FavoriteMovieRepository favoriteMovieRepository;

    public MovieServiceImpl(MovieRepository movieRepository, FavoriteMovieRepository favoriteMovieRepository, UserRepository userRepository) {
        this.movieRepository = movieRepository;
        this.favoriteMovieRepository = favoriteMovieRepository;
        this.userRepository = userRepository;
    }


    @Override
    public Movie saveMovieToDb(Movie movie) {

//        if (movie.getTitle() == null || movie.getPosterPath() == null) {
//            return new RuntimeException("не айден title или poster_path");
//        }
        return movieRepository.save(movie);
    }

    @Override
    public Page<Movie> getAllMovies(Pageable pageable) {
        return movieRepository.findAll(pageable);
    }
    @Override
    public void addMovieToFavorites(Authentication authentication, Movie movie) {
        String username = authentication.getName();
        User thisUser = userRepository.findByUsername(username);
        favoriteMovieRepository.save(new FavoritesMovie(thisUser, movie));
    }


    @Override
    public void removeMovieFromFavorites(Authentication authentication, Movie movie) {
        String username = authentication.getName();
        User thisUser = userRepository.findByUsername(username);
        FavoritesMovie favoritesMovie = favoriteMovieRepository.getFavoriteMovieForCurrentUser(thisUser,movie);
        if (favoritesMovie!=null){
            favoriteMovieRepository.delete(favoritesMovie);
        }
    }

    @Override
    public List<Movie> notInFavoritesMoviesInfo(Authentication authentication,Pageable pagable) {
        String username = authentication.getName();
        User thisUser = userRepository.findByUsername(username);
        List<FavoritesMovie> favoritesMovieList = favoriteMovieRepository.findAllMoviesByUser(thisUser);
        List<Movie> allMovies = movieRepository.findAll();
        List<Movie> notInFavoritesMoviesList = new ArrayList<>();
        for (Movie movie : allMovies) {
            boolean isFavorites = favoritesMovieList.stream()
                    .anyMatch(favoritesMovie -> favoritesMovie.getMovie().equals(movie));
            if (!isFavorites) {
                notInFavoritesMoviesList.add(movie);
            }
        }
        return notInFavoritesMoviesList;
    }

    @Override
    public List<FavoritesMovie> favoritesMoviesInfo(Authentication authentication) {
        String username = authentication.getName();
        User thisUser = userRepository.findByUsername(username);
        List<FavoritesMovie> favoritesMovieList = favoriteMovieRepository.findAllMoviesByUser(thisUser);
        return favoritesMovieList;
    }

    @Override
    public Movie findMovieById(Long moveId) {
        return movieRepository.findMovieById(moveId);
    }

    @Override
    public Movie findMovieByTitle(String title) {
        return movieRepository.findMovieByTitle(title);
    }


}