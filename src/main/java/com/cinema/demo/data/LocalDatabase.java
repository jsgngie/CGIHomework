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
import java.util.regex.*;
import java.util.Arrays;
import java.util.HashSet;


@Service
public class LocalDatabase {

    private final MovieRepository movieRepository;
    private final RatingRepository ratingRepository;

    private final HashSet<String> chosen;

    private final HashSet<String> filter;
    @Autowired
    public LocalDatabase(MovieRepository movieRepository, RatingRepository ratingRepository) {
        this.movieRepository = movieRepository;
        this.ratingRepository = ratingRepository;
        this.chosen = new HashSet<>();
        this.filter = createFilter();
    }

    /**
     * @param skip - specifies whether to skip first 800000 rows
     *             Reads in movie data from .tsv file and filters it, writes it to the database.
     */
    public void readRatingTSV(boolean skip) {

        try (BufferedReader br = new BufferedReader(new FileReader("src/main/java/com/cinema/demo/data/title.ratings.tsv"))) {
            String line;
            boolean isFirst = true;

            if (skip) {
                for (int i = 0; i < 800000; i++) {
                    System.out.println("Skipping");
                    br.readLine();
                }
            }

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

    /**
     * @param skip - specifies whether to skip first 800000 rows
     *             Reads in movie data from .tsv file and filters it, writes it to the database.
     */
    public void readMoviesTSV(boolean skip) {

        try (BufferedReader br = new BufferedReader(new FileReader("src/main/java/com/cinema/demo/data/title.basics.tsv"))) {
            String line;
            boolean isFirst = true;

            //If skip variable is set true
            if (skip) {
                for (int i = 0; i < 800000; i++) {
                    System.out.println("Skipping");
                    br.readLine();
                }
            }

            while ((line = br.readLine()) != null) {

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

                //Checks for adult movies, invalid values.
                if (parseNullableInt(parts[7]) == null || !titleType.equals("movie") || isAdult == 1 || originalTitle.length() > 255 || primaryTitle.length() > 255) continue;

                String[] nameParts = primaryTitle.split(",");

                //profanity filter
                boolean flag = false;

                for (String part : nameParts) {
                    if (filter.contains(part)) {
                        flag = true;
                        break;
                    }
                }

                if (flag) continue;

                Movie newMovie = new Movie();

                //Setting values
                newMovie.setMovieId(movieId);
                newMovie.setTitleType(checkNull(titleType));
                newMovie.setPrimaryTitle(checkNull(primaryTitle));
                newMovie.setOriginalTitle(checkNull(originalTitle));
                newMovie.setStartYear(parseNullableInt(parts[5]));
                newMovie.setRuntimeMinutes(parseNullableInt(parts[7]));
                newMovie.setGenres(checkNull(parts[8]));

                //Adding movie to chosen hashset for later rating reading optimization.
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

    /**
     * Reads in various profanity from a blacklist.txt file provided by Youtube, to later check movie titles for profanity.
     * @return HashSet full of profanity.
     */
    public static HashSet<String> createFilter() {
        HashSet<String> filter = new HashSet<>();
        String line;

        try (BufferedReader br = new BufferedReader(new FileReader("src/main/java/com/cinema/demo/data/blacklist.txt"))) {
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                filter.addAll(Arrays.asList(parts));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filter;
    }

    /**
     * Reads in parsed data from .csv files.
     */
    public void readAll() {

        readMoviesCSV();
        System.out.println("Finished adding movies");

        readRatingsCSV();
        System.out.println("Finished adding ratings");
    }

    /**
     * Reads movies.csv for database.
     */
    public void readMoviesCSV() {
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/java/com/cinema/demo/data/movieTable.csv"))) {
            String line;
            boolean isFirst = true;

            while ((line = br.readLine()) != null) {

                /**
                if (line.contains("\"")) {
                    Pattern pattern = Pattern.compile("\"([^\"]*)\"");
                    Matcher matcher = pattern.matcher(line);
                    while (matcher.find()) {
                        String genres = matcher.group(1);
                    }
                } else {
                    String[] parts = line.split(",");
                } **/

                String[] parts = line.split(",");
                String runtime = parts[0];
                String startyear = parts[1];
                String genres = parts[3];
                String movieId = parts[4];
                String originalTitle = parts[5];
                String primaryTitle = parts[6];
                String titleType = parts[7];

                Movie newMovie = new Movie();

                //Setting values
                newMovie.setMovieId(movieId);
                newMovie.setTitleType(checkNull(titleType));
                newMovie.setPrimaryTitle(checkNull(primaryTitle));
                newMovie.setOriginalTitle(checkNull(originalTitle));
                newMovie.setStartYear(parseNullableInt(startyear));
                newMovie.setRuntimeMinutes(parseNullableInt(runtime));
                newMovie.setGenres(checkNull(genres));

                System.out.println("Adding movie....");
                movieRepository.save(newMovie);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads ratings.csv file for database.
     */
    public void readRatingsCSV() {
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/java/com/cinema/demo/data/ratingTable.csv"))) {
            String line;

            while ((line = br.readLine()) != null) {

                String[] parts = line.split(",");

                Rating newRating = new Rating();

                newRating.setRating(Double.parseDouble(parts[0]));
                newRating.setMovieId(parts[2]);
                newRating.setRatingCount(Integer.parseInt(parts[2]));

                System.out.println("Adding rating...");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
