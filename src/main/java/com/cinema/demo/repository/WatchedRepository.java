package com.cinema.demo.repository;

import com.cinema.demo.model.WatchedMovies;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WatchedRepository extends CrudRepository<WatchedMovies, Integer> {
}
