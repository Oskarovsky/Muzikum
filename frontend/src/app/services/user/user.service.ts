import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from '../../../environments/environment';
import {Playlist} from '../../playlists/playlist/model/playlist';
import {User} from './user';

const API: string = environment.serverUrl;
const AUTH_API = API + '/auth';
const USER_API = API + '/user';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private httpClient: HttpClient) { }

  getPublicContent(): Observable<any> {
    return this.httpClient.get(AUTH_API + '/all', { responseType: 'text' });
  }

  getUserBoard(): Observable<any> {
    return this.httpClient.get(AUTH_API + '/user', { responseType: 'text' });
  }

  getModeratorBoard(): Observable<any> {
    return this.httpClient.get(AUTH_API + '/mod', { responseType: 'text' });
  }

  getAdminBoard(): Observable<any> {
    return this.httpClient.get(AUTH_API + '/admin', { responseType: 'text' });
  }

  /** GET last added users */
  getLastAddedUsers(numberOfUsers: string): Observable<User[]> {
    return this.httpClient.get<User[]>(USER_API + '/lastAdded/' + numberOfUsers);
  }
}
