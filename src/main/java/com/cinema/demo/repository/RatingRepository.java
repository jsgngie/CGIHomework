package com.cinema.demo.repository;

import com.cinema.demo.model.Movie;
import com.cinema.demo.model.Rating;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RatingRepository extends CrudRepository<Rating, Integer> {
    public Optional<Rating> findByMovieId(String id);
}
