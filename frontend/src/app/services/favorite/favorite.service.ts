import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Track} from '../../tracks/track/model/track';
import {environment} from '../../../environments/environment';

const API: string = environment.serverUrl;
const FAVORITES_API = API + '/favorites';

@Injectable({ providedIn: 'root' })
export class FavoriteService {

  constructor(private http: HttpClient) { }

  getAllFavoritesTracksByUsername(username: string): Observable<any> {
    return this.http.get<Track[]>(FAVORITES_API + '/' + username + '/tracks');
  }

  getAllFavoritesTracksIdsByUsername(username: string): Observable<any> {
    return this.http.get<number[]>(FAVORITES_API + '/' + username + '/tracks/ids');
  }
}
