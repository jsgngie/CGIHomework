import { Component, OnInit } from '@angular/core';
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

  movies!: Movie[];
  genres: string[] = [];

  startIdx: number = 0;
  endIdx: number = 5;

  ratings: any;

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

  public getNumber(): number {
    return this.startIdx;
  }

  public getMoviesSlice(): any[] {
    return this.movies.slice(this.startIdx, this.endIdx);
  }

  public updateMovieSlice(startIndex: number): void {
    this.startIdx = startIndex;
    this.endIdx = startIndex + 5;
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

    this.ratingService.getMovieRating(movie.movie_id).subscribe(
      (response: Rating[]) => {
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

}
