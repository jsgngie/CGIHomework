import { Component, HostListener, OnInit } from '@angular/core';
import { MovieService } from './movie.service';
import { HttpErrorResponse } from '@angular/common/http';
import { Movie } from './movie';
import { RatingService } from './rating.service';
import { Observable, filter } from 'rxjs';
import { Rating } from './rating';
import { watchedMovie } from './watchedMovie';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit {
  title = 'cinemaClient';

  selectedMovie: any;
  isPopupVisible: boolean = false;
  popUpImg: any;
  dayIndex: number = -1;

  movies!: Movie[];
  filteredMovies!: Movie[];
  genres: string[] = [];

  ratings!: Rating;

  selectedGenre: string = "";

  weights!: Map<String, number>;
  
  recommendationTriggered = false;
  recommendedMovies!: Movie[];

  constructor(private movieService: MovieService, private ratingService: RatingService) {}

  ngOnInit(): void {
    this.getMovies();
  }

  public getMovies(): void {
    this.movieService.getMovies().subscribe(
      (response: Movie[]) => {
        this.movies = response;
        this.extractGenres();},
      (error: HttpErrorResponse) => {console.log(error.message);}
    );
  }

  public getRatings(): Rating {
    return this.ratings;
  }
  
  public extractGenres(): void {
    this.movies.forEach((movie: Movie) => {
      if (movie.genres) {
      
        var split = movie.genres.split(",");
        split.forEach((genre: string) => {
          
          if (!this.genres.includes(genre)) {
            this.genres.push(genre);
          }
        });
      }
    });
    console.log(this.genres);
  }

  
  showPopup(movie: any, popUpImg: any): void {
    this.selectedMovie = movie;
    this.ratingService.getMovieRating(this.selectedMovie.movieId).subscribe(
      (response: Rating) => {
        this.ratings = response;
      },
      (error: HttpErrorResponse) => {console.log(error.message);}
    );
    
    this.popUpImg = popUpImg;
    this.isPopupVisible = true; 
  }

  
  closePopup(): void {
    this.selectedMovie = null;
    this.isPopupVisible = false;
  }

  filterMovies() {
    let filteredMovies = this.movies;

    if (this.dayIndex !== -1) {
      const startIndex = this.dayIndex * 5;
      const endIndex = startIndex + 4;
      filteredMovies = filteredMovies.slice(startIndex, endIndex + 1);
    }

    if (this.selectedGenre !== "") {

      const selectedGenres = this.selectedGenre.split(',');


      filteredMovies = filteredMovies.filter(movie => {
  
        const movieGenres = movie.genres.split(',');

        return selectedGenres.some(selectedGenre => movieGenres.includes(selectedGenre.trim()));
    });
  }

    return filteredMovies;
  }

  getMovieImagePath(index: number): string {
    return `../assets/pics/Movie${index+1}.png`;
  }

  submitMovie(movie: Movie): void {
    console.log(movie)
    var watchedMovie: watchedMovie = {
      movie_id: movie.movie_id,
      titleType: movie.titleType,
      primaryTitle: movie.primaryTitle,
      originalTitle: movie.originalTitle,
      startYear: movie.startYear,
      runtimeMinutes: movie.runtimeMinutes,
      genres: movie.genres
    }
    this.movieService.watchMovie(watchedMovie).subscribe(
      (response: watchedMovie) => {console.log(response)},
      (response: HttpErrorResponse) => {console.log(response.message)}
    );
    
    setTimeout(() => {
      window.location.reload();
  }, 1000);
  }

  recommendMovies() {

    this.movieService.getWatched().subscribe(
      (response: Map<String, number>) => {this.weights = new Map(Object.entries(response));},
      
      (response: HttpErrorResponse) => {console.log(response.message)}
    );
    
    var values = new Map<Movie, number>();

    if (this.weights && this.weights.size > 0) {
      var count = 0;

      for (let i = 0; i < this.movies.length; i++) {
        var score = 0;

        var genres = this.movies[i].genres.split(",")
        
        genres.forEach(genre => {
          score += this.weights.get(genre) || 0;
        });

        values.set(this.movies[i], score);
    
      }

      var sortedEntries = Array.from(values.entries());

      sortedEntries.sort((a, b) => b[1] - a[1]);

      var topFive = sortedEntries.slice(0, 5);

      var topFiveKeys = topFive.map(entry => entry[0]);
  

      this.recommendedMovies = topFiveKeys
    }
  }
}