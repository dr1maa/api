package com.application.api.model;


import javax.persistence.*;
import java.util.UUID;

@Entity
public class FavoritesMovie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    public FavoritesMovie(User user, Movie movie) {
        this.user = user;
        this.movie = movie;
    }

    public Long getFavoriteId() {
        return id;
    }

    public void setFavoriteId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public FavoritesMovie() {
    }
}