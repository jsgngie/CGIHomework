package com.cinema.demo.data;

import com.cinema.demo.model.Movie;
import com.cinema.demo.model.Rating;
import com.cinema.demo.repository.MovieRepository;
import com.cinema.demo.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class LocalDatabase {

    private final MovieRepository movieRepository;
    private final RatingRepository ratingRepository;

    private HashSet<String> chosen;

    @Autowired
    public LocalDatabase(MovieRepository movieRepository, RatingRepository ratingRepository) {
        this.movieRepository = movieRepository;
        this.ratingRepository = ratingRepository;
        this.chosen = new HashSet<>();
    }

    public void readRatingTSV() {

        try (BufferedReader br = new BufferedReader(new FileReader("src/main/java/com/cinema/demo/data/title.ratings.tsv"))) {
            String line;
            boolean isFirst = true;

            while ((line = br.readLine()) != null) {

                //skip column headers
                if (isFirst) {
                    isFirst = false;
                    continue;
                }

                String[] parts = line.split("\t");

                //optimizer
                if (!chosen.contains(parts[0])) continue;

                Rating newRating = new Rating();


                newRating.setRating(Double.parseDouble(parts[1]));
                newRating.setMovieId(parts[0]);
                newRating.setRatingCount(Integer.parseInt(parts[2]));

                System.out.println("Adding rating...");
                ratingRepository.save(newRating);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Finished adding ratings.");
    }

    public void readMoviesTSV() {

        try (BufferedReader br = new BufferedReader(new FileReader("src/main/java/com/cinema/demo/data/title.basics.tsv"))) {
            String line;
            boolean isFirst = true;

            while ((line = br.readLine()) != null) {
                line = br.readLine();

                //skip column headers
                if (isFirst) {
                    isFirst = false;
                    continue;
                }

                String[] parts = line.split("\t");

                String movieId = parts[0];
                String titleType = parts[1];
                String primaryTitle = parts[2];
                String originalTitle = parts[3];
                int isAdult = Integer.parseInt(parts[4]);

                //Checks
                if (parseNullableInt(parts[7]) == null || !titleType.equals("movie") || isAdult == 1 || originalTitle.length() > 255 || primaryTitle.length() > 255) continue;

                Movie newMovie = new Movie();

                //Setting values
                newMovie.setMovieId(movieId);
                newMovie.setTitleType(checkNull(titleType));
                newMovie.setPrimaryTitle(checkNull(primaryTitle));
                newMovie.setOriginalTitle(checkNull(originalTitle));
                newMovie.setStartYear(parseNullableInt(parts[5]));
                newMovie.setRuntimeMinutes(parseNullableInt(parts[7]));
                newMovie.setGenres(checkNull(parts[8]));

                chosen.add(movieId);
                System.out.println("Adding movie....");
                movieRepository.save(newMovie);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Finished adding movies");
    }

    private static Integer parseNullableInt(String value) {
        return "\\N".equals(value) ? null : Integer.parseInt(value);
    }

    private static String checkNull(String value) {
        return "\\N".equals(value) ? null : value;
    }
}
