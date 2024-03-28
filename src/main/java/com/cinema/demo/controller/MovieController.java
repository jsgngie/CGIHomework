package com.cinema.demo.controller;

import com.cinema.demo.data.LocalDatabase;

import com.cinema.demo.model.Movie;
import com.cinema.demo.model.Rating;
import com.cinema.demo.services.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        database.readMoviesTSV();
        database.readRatingTSV();
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
}
