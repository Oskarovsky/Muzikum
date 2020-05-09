import { Component, OnInit } from '@angular/core';
import {Playlist} from '../../playlists/playlist/model/playlist';
import {Subscription} from 'rxjs';
import {PlaylistService} from '../../services/playlist/playlist.service';
import {TokenStorageService} from '../../services/auth/token-storage.service';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-sidebar-left',
  templateUrl: './sidebar-left.component.html',
  styleUrls: ['./sidebar-left.component.css']
})
export class SidebarLeftComponent implements OnInit {

  playlists: Playlist[] = [];
  sub: Subscription;

  constructor(private playlistService: PlaylistService,
              private tokenStorage: TokenStorageService,
              private route: ActivatedRoute) { }

  ngOnInit() {
    if (this.tokenStorage.getToken()) {
      this.getLastAddedPlaylists('5');
    }
  }

  public getLastAddedPlaylists(numberOfPlaylists: string) {
    this.playlistService.getLastAddedPlaylists(numberOfPlaylists).subscribe(
      response => {
        this.playlists = response;
      },
      error => {
        alert('An error with fetching last added playlist has occurred');
      }
    );
  }
}
