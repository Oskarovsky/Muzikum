import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth/auth.service';
import { TokenStorageService } from '../../services/auth/token-storage.service';
import { FACEBOOK_AUTH_URL } from '../../../assets/constants/app.const';
import {AlertService} from '../../services/alert/alert.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  form: any = {};
  isLoggedIn = false;
  roles: string[] = [];

  facebookAuthUrl = FACEBOOK_AUTH_URL;

  constructor(private authService: AuthService,
              private tokenStorage: TokenStorageService,
              private alertService: AlertService) { }

  ngOnInit() {
    if (this.tokenStorage.getToken()) {
      this.isLoggedIn = true;
      this.roles = this.tokenStorage.getUser().roles;
    }
  }

  onSubmit() {
    this.authService.login(this.form).subscribe(
      data => {
        this.tokenStorage.saveToken(data.accessToken);
        this.tokenStorage.saveUser(data);
        this.isLoggedIn = true;
        this.roles = this.tokenStorage.getUser().roles;
        this.reloadPage();
      },
      error => {
        this.alertService.error('Wystąpił błąd w trakcie logowania. Spróbuj ponownie!');
      }
    );
  }

  reloadPage() {
    window.location.reload();
  }

}
