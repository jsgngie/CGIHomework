package com.cinema.demo.services;

import com.cinema.demo.model.Movie;
import com.cinema.demo.model.Rating;
import com.cinema.demo.model.WatchedMovies;
import com.cinema.demo.repository.MovieRepository;
import com.cinema.demo.repository.RatingRepository;
import com.cinema.demo.repository.WatchedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {
    private final MovieRepository movieRepository;
    private final RatingRepository ratingRepository;

    private final WatchedRepository watchedRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository, RatingRepository ratingRepository, WatchedRepository watchedRepository) {
        this.movieRepository = movieRepository;
        this.ratingRepository = ratingRepository;
        this.watchedRepository = watchedRepository;
    }

    /**
     * @return a list of thirty-five random movies.
     */
    public List<Movie> findRandomThirtyFive() {
        return movieRepository.findRandomMoviesThirtyFive();
    }

    /**
     * @param id - id of movie which rating is searched for.
     * @return rating if found null if not
     */
    public Optional<Rating> findRatingByMovieId(String id) {
        return ratingRepository.findByMovieId(id);
    }

    /**
     *
     * @param movie - movie to be added to the watched movies table of the database
     * @return - response
     */
    public WatchedMovies watchMovie(WatchedMovies movie) { return watchedRepository.save(movie);}

    /**
     * @return Finds all instances of watched movies.
     */
    public Iterable<WatchedMovies> findWatched() { return watchedRepository.findAll();}
}
