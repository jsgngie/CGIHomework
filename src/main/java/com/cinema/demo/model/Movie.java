package com.cinema.demo.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "movie", schema = "public")

public class Movie {

    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "movie_id")
    private String movieId;

    @Column(name = "titleType")
    private String titleType;

    @Column(name = "primaryTitle")
    private String primaryTitle;

    @Column(name = "originalTitle")
    private String originalTitle;

    @Column(name = "startYear")
    private Integer startYear;

    @Column(name = "runtimeMinutes")
    private Integer runtimeMinutes;

    @Column(name = "genres")
    private String genres;
}
