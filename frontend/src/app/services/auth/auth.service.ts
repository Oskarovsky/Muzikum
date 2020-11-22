import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import { Observable, BehaviorSubject } from 'rxjs';
import {TokenStorageService} from './token-storage.service';
import { environment } from 'src/environments/environment';
import {PasswordChangeDto} from './password-change-dto';
import { Router } from '@angular/router';
import { User } from '../user/user';

const API: string = environment.serverUrl;
const AUTH_API = API + '/auth';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private httpClient: HttpClient, private router: Router,
              private tokenStorageService: TokenStorageService) {
  }


  login(credentials): Observable<any> {
    return this.httpClient.post(AUTH_API + '/signin', {
      usernameOrEmail: credentials.username,
      password: credentials.password
    }, httpOptions);
  }

  register(user): Observable<any> {
    return this.httpClient.post(AUTH_API + '/signup', {
      username: user.username,
      email: user.email,
      password: user.password
    }, httpOptions);
  }

  getUserByToken(token: string): Observable<any> {
    return this.httpClient.get<string>(AUTH_API + '/token/' + token);
  }

  changeUserPassword(passwordDto): Observable<any> {
    return this.httpClient.post(AUTH_API + '/updatePassword', {
      email: passwordDto.email,
      oldPassword: passwordDto.oldPassword,
      newPassword: passwordDto.newPassword
    }, httpOptions);
  }

  resetPassword(userEmail: string): Observable<any> {
    return this.httpClient.post(AUTH_API + '/resetPassword',
      { params: new HttpParams().set('email', userEmail)});
  }

}
