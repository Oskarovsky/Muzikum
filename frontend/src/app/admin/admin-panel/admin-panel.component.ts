import { Component, OnInit } from '@angular/core';
import {User} from '../../services/user/user';
import {Playlist} from '../../playlists/playlist/model/playlist';
import {Track} from '../../tracks/track/model/track';
import {TokenStorageService} from '../../services/auth/token-storage.service';

@Component({
  selector: 'app-admin-panel',
  templateUrl: './admin-panel.component.html',
  styleUrls: ['./admin-panel.component.css']
})
export class AdminPanelComponent implements OnInit {

  playlists: Playlist[] = [];
  tracks: Track[] = [];
  isUserLogged = false;
  user: null;

  modelUser: User = {
    id: null,
    username: '',
    email: '',
    password: ''
  };

  constructor(private tokenStorageService: TokenStorageService) { }

  ngOnInit() {
    this.isUserLogged = !!this.tokenStorageService.getToken();
  }

}
