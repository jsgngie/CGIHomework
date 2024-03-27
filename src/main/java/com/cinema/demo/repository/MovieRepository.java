package com.cinema.demo.repository;

import com.cinema.demo.model.Movie;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends CrudRepository<Movie, Integer> {
    public Optional<Movie> findByMovieId(String id);

    @Query("SELECT m FROM Movie m ORDER BY RANDOM() LIMIT 35")
    public List<Movie> findRandomMoviesThirtyFive();
}
