import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Rating } from './rating';

@Injectable({
  providedIn: 'root'
})
export class RatingService {
  private baseUrl = 'http://localhost:8080/api/movies';
  
  constructor(private http: HttpClient) { }

  public getMovieRating(id: string): Observable<Rating[]> {
    return this.http.get<any>(`${this.baseUrl}/findRating/${id}`);
  }
}
