import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ACCESS_TOKEN } from 'src/assets/constants/app.const';
import {TokenStorageService} from '../../services/auth/token-storage.service';
import {AuthService} from '../../services/auth/auth.service';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from '../../../environments/environment';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

const API: string = environment.serverUrl;
const AUTH_API = API + '/auth';

@Component({
  selector: 'app-oauth2-redirect-handler',
  templateUrl: './oauth2-redirect-handler.component.html',
  styleUrls: ['./oauth2-redirect-handler.component.scss']
})
export class Oauth2RedirectHandlerComponent implements OnInit {

  result: string;

  form: any = {};
  isLoggedIn = false;
  isLoginFailed = false;
  errorMessage = '';
  roles: string[] = [];
  errorParam: string;
  tokenParam: string;

  constructor(private activatedRoute: ActivatedRoute, private router: Router,
              private tokenStorage: TokenStorageService, private authService: AuthService,
              private httpClient: HttpClient) {
    this.activatedRoute.queryParams.subscribe(params => {
      this.tokenParam = params.token;
      this.errorParam = params.error;
    });

    if (this.tokenParam) {
      this.authService.getUserByToken(this.tokenParam).subscribe(
        data => {
          this.tokenStorage.saveToken(this.tokenParam);
          this.tokenStorage.saveUser(data);
          this.isLoginFailed = false;
          this.isLoggedIn = true;
          this.roles = this.tokenStorage.getUser().roles;
        }, error => {
          console.log(error);
        });
      this.result = 'Zalogowałeś się w portalu oskarro.com ! Poczekaj 5 sekund albo kliknij tutaj - ';
    } else {
      this.result = 'Wystąpił błąd podczas rejestracji! Poczekaj 5 sekund albo wróć do panelu logowania - ';
      this.router.navigateByUrl('/login');
    }
  }

  ngOnInit() {
      setTimeout(() => {
        window.location.replace('/');
        alert('5 Seconds has passed! The page will now refresh');
    }, 5000);
  }

  reloadPage() {
    window.location.reload();
  }

}
