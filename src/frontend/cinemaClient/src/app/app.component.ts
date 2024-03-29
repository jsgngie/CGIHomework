import { Component, HostListener, OnInit } from '@angular/core';
import { MovieService } from './movie.service';
import { HttpErrorResponse } from '@angular/common/http';
import { Movie } from './movie';
import { RatingService } from './rating.service';
import { Observable } from 'rxjs';
import { Rating } from './rating';

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
  genres: string[] = [];

  ratings!: Rating;

  selectedGenre: string = "";

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
    window.location.reload();
    this.movieService.watchMovie(movie);
  }


}
