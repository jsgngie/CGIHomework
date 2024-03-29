package com.cinema.demo.controller;

import com.cinema.demo.data.LocalDatabase;

import com.cinema.demo.model.Movie;
import com.cinema.demo.model.Rating;
import com.cinema.demo.model.WatchedMovies;
import com.cinema.demo.services.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    private final LocalDatabase database;

    //DEV
    @GetMapping("/readMovies")
    public String readBasics() {
        database.readMoviesTSV(true);
        database.readRatingTSV(false);
        return "data read";
    }

    @GetMapping("/readRating")
    public String readRatings() {
        database.readRatingTSV(false);
        return "data read";
    }

    @GetMapping("/randomMovies")
    public ResponseEntity<List<Movie>> getRandomMovies() {
        List<Movie> randomMovies = movieService.findRandomThirtyFive();
        return new ResponseEntity<>(randomMovies, HttpStatus.OK);
    }

    @GetMapping("/findRating/{id}")
    public ResponseEntity<Optional<Rating>> getRatingById(@PathVariable String id) {
        Optional<Rating> rating = movieService.findRatingByMovieId(id);
        return new ResponseEntity<>(rating, HttpStatus.OK);
    }

    @PostMapping("/watchMovie")
    public ResponseEntity<WatchedMovies> watchMovie(@RequestBody WatchedMovies movie) {
        WatchedMovies newMovie = movieService.watchMovie(movie);
        return new ResponseEntity<>(newMovie, HttpStatus.CREATED);
    }

    @GetMapping("/getWatched")
    public ResponseEntity<HashMap<String, Integer>> watchMovie() {
        Iterable<WatchedMovies> movies = movieService.findWatched();

        HashMap<String, Integer> counts = new HashMap<>();
        for (WatchedMovies movie : movies) {
            String[] genres = movie.getGenres().split(",");
            for (String genre : genres) {
                if (counts.containsKey(genre)) {
                    counts.put(genre, counts.get(genre)+1);
                } else {
                    counts.put(genre, 1);
                }
            }
        }

        return new ResponseEntity<>(counts, HttpStatus.OK);
    }
}
