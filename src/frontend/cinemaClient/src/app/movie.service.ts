import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Movie } from './movie';

@Injectable({
  providedIn: 'root'
})
export class MovieService {
  private baseUrl = 'http://localhost:8080/api/movies';
  constructor(private http: HttpClient) { }

  public getMovies(): Observable<Movie[]> {
    const url = `${this.baseUrl}/randomMovies`;
    return this.http.get<Movie[]>(url);
}

public watchMovie(movie: Movie): Observable<Movie> {
    const url = `${this.baseUrl}/watchMovie`;
    return this.http.post<Movie>(url, {movie});
}
}
