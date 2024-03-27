package com.cinema.demo.controller;

import com.cinema.demo.data.LocalDatabase;
import com.cinema.demo.repository.MovieRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieRepository repository;

    private final LocalDatabase database;


    @GetMapping("/readMovies")
    public String readBasics() {
        database.readMoviesTSV();
        return "data read";

    }
}
