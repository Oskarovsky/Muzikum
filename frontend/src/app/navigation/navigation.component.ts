import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { TokenStorageService } from '../services/auth/token-storage.service';

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.css']
})
export class NavigationComponent implements OnInit {

  private roles: string[];
  isLoggedIn = false;
  showAdminBoard = false;
  showModeratorBoard = false;
  username: string;

  @Output() public sidenavToggle = new EventEmitter();

  constructor(private http: HttpClient,
              private tokenStorageService: TokenStorageService) {}

  title = 'Oskarro.com';

  ngOnInit() {
    this.isLoggedIn = !!this.tokenStorageService.getToken();

    if (this.isLoggedIn) {
      const user = this.tokenStorageService.getUser();
      this.roles = user.roles;

      this.showAdminBoard = this.roles.includes('ROLE_ADMIN');
      this.showModeratorBoard = this.roles.includes('ROLE_MODERATOR');

      this.username = user.username;
    }
  }

  logout() {
    this.tokenStorageService.signOut();
    window.location.reload();
  }

  public onToggleSidenav = () => {
    this.sidenavToggle.emit();
  }

}
