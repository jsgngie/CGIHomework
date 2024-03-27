package com.cinema.demo.repository;

import com.cinema.demo.model.Movie;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MovieRepository extends CrudRepository<Movie, Integer> {
    public Optional<Movie> findByMovieId(String id);
}
