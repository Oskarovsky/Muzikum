import { Component, OnInit } from '@angular/core';
import {User} from '../../services/user/user';
import {Playlist} from '../../playlists/playlist/model/playlist';
import {Track} from '../../tracks/track/model/track';
import {TokenStorageService} from '../../services/auth/token-storage.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-admin-panel',
  templateUrl: './admin-panel.component.html',
  styleUrls: ['./admin-panel.component.css']
})
export class AdminPanelComponent implements OnInit {

  playlists: Playlist[] = [];
  tracks: Track[] = [];
  isUserLogged = false;
  isAdmin = false;
  user: null;

  modelUser: User = {
    id: null,
    username: '',
    email: '',
    password: '',
    createdAt: '',
    favoriteTracks: null,
    imageUrl: '',
    provider: '',
    providerId: null
  };

  constructor(private router: Router,
              private tokenStorageService: TokenStorageService) { }

  ngOnInit() {
    this.isUserLogged = !!this.tokenStorageService.getToken();

    if (this.isUserLogged) {
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
