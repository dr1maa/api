package com.application.api.repository;


import com.application.api.model.FavoritesMovie;
import com.application.api.model.Movie;
import com.application.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FavoriteMovieRepository extends JpaRepository<FavoritesMovie, UUID> {
    List<FavoritesMovie> findAllMoviesByUser(User user);

    @Query("SELECT fm " +
            "FROM FavoritesMovie fm " +
            "WHERE fm.user = :user " +
            "AND fm.movie = :movie")
    FavoritesMovie getFavoriteMovieForCurrentUser(@Param("user") User user, @Param("movie") Movie movie);
}