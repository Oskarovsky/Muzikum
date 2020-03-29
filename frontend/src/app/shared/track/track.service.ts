import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({providedIn: 'root'})
export class TrackService {

  public API = '//localhost:8080';
  public TRACK_API = this.API + '/tracks';
  public PROVIDER_API = this.API + '/providers';


  constructor(private http: HttpClient) {
  }

  getAll(): Observable<any> {
    return this.http.get(this.TRACK_API + '/findAll');
  }

  get(id: string) {
    return this.http.get(this.TRACK_API + '/' + id);
  }

  getFromProviderByGenre(id: string, genre: string) {
    return this.http.get(this.TRACK_API + '/' + id + '/' + genre);
  }

  getFromProvider(id: string) {
    return this.http.get(this.PROVIDER_API + '/' + id + '/tracks');
  }

}
