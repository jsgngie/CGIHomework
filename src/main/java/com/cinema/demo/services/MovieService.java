package com.cinema.demo.services;

import com.cinema.demo.model.Movie;
import com.cinema.demo.repository.MovieRepository;
import com.cinema.demo.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {
    private final MovieRepository movieRepository;
    private final RatingRepository ratingRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository, RatingRepository ratingRepository) {
        this.movieRepository = movieRepository;
        this.ratingRepository = ratingRepository;
    }

    public List<Movie> findRandomThirtyFive() {
        return movieRepository.findRandomMoviesThirtyFive();
    }
}
