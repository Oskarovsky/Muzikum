import { Component, OnInit } from '@angular/core';
import {Playlist} from '../../playlists/playlist/model/playlist';
import {Subscription} from 'rxjs';
import {PlaylistService} from '../../services/playlist/playlist.service';
import {TokenStorageService} from '../../services/auth/token-storage.service';
import {ActivatedRoute} from '@angular/router';
import {UserService} from '../../services/user/user.service';
import {User} from '../../services/user/user';
import {Track} from '../../tracks/track/model/track';
import {TrackService} from '../../services/track/track.service';

@Component({
  selector: 'app-sidebar-left',
  templateUrl: './sidebar-left.component.html',
  styleUrls: ['./sidebar-left.component.css']
})
export class SidebarLeftComponent implements OnInit {

  users: User[] = [];
  playlists: Playlist[] = [];
  sub: Subscription;
  randomTrack: Track;

  constructor(private playlistService: PlaylistService,
              private trackService: TrackService,
              private userService: UserService,
              private tokenStorage: TokenStorageService,
              private route: ActivatedRoute) { }

  ngOnInit() {
    if (this.tokenStorage.getToken()) {
      this.getLastAddedPlaylists('5');
      this.getLastAddedUsers('5');
      this.getRandomTrack();
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

  public getLastAddedUsers(numberOfUsers: string) {
    this.userService.getLastAddedUsers(numberOfUsers).subscribe(
      response => {
        this.users = response;
      },
      error => {
        alert('An error with fetching last added users has occurred');
      }
    );
  }

  public getRandomTrack() {
    this.trackService.getRandomTrack().subscribe(
      response => {
        this.randomTrack = response;
      },
      error => {
        alert('An error with fetching random track has occurred');
      }
    );
  }
}
