import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

// const API = '//localhost:8080/api';
const API = '//91.205.75.145:8080/api';
const AUTH_API = 'http://localhost:8080/api/auth';

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


}
