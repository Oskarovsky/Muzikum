import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/user/user.service';
import { Router } from '@angular/router';
import {TokenStorageService} from '../../services/auth/token-storage.service';

@Component({
  selector: 'app-board-admin',
  templateUrl: './board-admin.component.html',
  styleUrls: ['./board-admin.component.css']
})
export class BoardAdminComponent implements OnInit {

  content = '';
  isAdmin = false;
  isLoggedIn = false;

  constructor(private router: Router,
              private tokenStorageService: TokenStorageService) { }

  ngOnInit() {
    this.isLoggedIn = !!this.tokenStorageService.getToken();

    if (this.isLoggedIn) {
      const user = this.tokenStorageService.getUser();
      this.isAdmin = user.roles.includes('ROLE_ADMIN');
      if (!this.isAdmin) {
        this.redirect();
      }
    } else {
      this.redirect();
    }
  }

  redirect() {
    this.router.navigate(['/']);
  }

}
