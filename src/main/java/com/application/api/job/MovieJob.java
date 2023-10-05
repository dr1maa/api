package com.application.api.job;

import com.application.api.model.Movie;
import com.application.api.service.MovieService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
public class MovieJob {

    private final ObjectMapper objectMapper;
    private final DiscoverClient discoverClient;
    private final MovieService movieService;
    private static final String TITLE = "title";
    private static final String POSTER_PATH = "poster_path";
    private static final String RESULT = "results";

    @Autowired
    public MovieJob(ObjectMapper objectMapper, DiscoverClient discoverClient, MovieService movieService) {
        this.objectMapper = objectMapper;
        this.discoverClient = discoverClient;
        this.movieService = movieService;
    }

    @Scheduled(fixedRate = 3000 * 60 * 60)
    void fetchAndSaveMoviesToDb() throws IOException {
        for (int page = 1; page <= 5; page++) {
            fetchAndSaveMoviesFromPage(page);
        }
    }

    private void fetchAndSaveMoviesFromPage(int page) throws IOException {
        Response response = discoverClient.fetchMoviesFromPage(page);
        if (response.isSuccessful()) {
            String responseBody = response.body().string();
            saveMovieFromResponse(responseBody);
        } else {
            System.err.println("Ошибка выполнения запроса" + response.code());
        }
    }

    private void saveMovieFromResponse(String responseBody) {
        try {
            JsonNode jsonNode = objectMapper.readTree(responseBody);
            JsonNode result = jsonNode.get(RESULT);
            if (result != null && result.isArray()) {
                for (JsonNode movieNode : result) {
                    String title = movieNode.get(TITLE).asText();
                    String posterPath = movieNode.get(POSTER_PATH).asText();
                    Optional<Movie> existingMovieOptional = Optional.ofNullable(movieService.findMovieByTitle(title));
                    if (existingMovieOptional.isPresent()) {
                        Movie existMovie = existingMovieOptional.get();
                        existMovie.setPosterPath(posterPath);
                        movieService.saveMovieToDb(existMovie);
                    } else {
                        Movie newMovie = new Movie();
                        newMovie.setTitle(title);
                        newMovie.setPosterPath(posterPath);
                        movieService.saveMovieToDb(newMovie);
                    }
                }
            }
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}