package com.cinema.demo.model;

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
@Table(name = "rating", schema = "public")
public class Rating {

    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "movie_id")
    private String movieId;

    @Column(name = "rating")
    private double rating;

    @Column(name = "ratingCount")
    private Integer ratingCount;

}
