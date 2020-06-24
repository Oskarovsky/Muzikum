import { Component, OnInit } from '@angular/core';
import {Playlist} from '../playlist/model/playlist';
import {PlaylistService} from '../../services/playlist/playlist.service';
import {TokenStorageService} from '../../services/auth/token-storage.service';

@Component({
  selector: 'app-playlist-all',
  templateUrl: './playlist-all.component.html',
  styleUrls: ['./playlist-all.component.css']
})
export class PlaylistAllComponent implements OnInit {

  playlists: Playlist[] = [];
  searchText: string;
  isLoggedIn = false;

  constructor(private playlistService: PlaylistService,
              private tokenStorage: TokenStorageService) { }

  ngOnInit() {
    if (this.tokenStorage.getToken()) {
      this.isLoggedIn = true;
    }
    this.getAllPlaylists();
  }

  public getAllPlaylists() {
    this.playlistService.getAllPlaylists().subscribe(
      res => {
        this.playlists = res;
      },
      error => {
        alert('An error with fetching playlists has occurred');
      }
    );
  }
}
