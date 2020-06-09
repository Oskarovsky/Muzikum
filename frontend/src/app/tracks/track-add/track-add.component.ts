import { Component, OnInit } from '@angular/core';
import { Track } from '../track/model/track';
import {Subscription} from 'rxjs';
import {TokenStorageService} from '../../services/auth/token-storage.service';
import {TrackService} from '../../services/track/track.service';

@Component({
  selector: 'app-track-add',
  templateUrl: './track-add.component.html',
  styleUrls: ['./track-add.component.scss']
})
export class TrackAddComponent implements OnInit {

  track: Track;
  sub: Subscription;
  isLoggedIn = false;
  username: string;
  showAdminBoard = false;

  constructor(private tokenStorage: TokenStorageService,
              private trackService: TrackService) { }

  ngOnInit() {
    if (this.tokenStorage.getToken()) {
      this.isLoggedIn = true;
      this.showAdminBoard = this.tokenStorage.getUser().roles.includes('ROLE_ADMIN');
    }
  }

  public addNewTrack(title: string, artist: string, version: string, genre: string) {
    const newTrack: Track = {
      id: null,
      title,
      artist,
      points: null,
      genre,
      version,
      url: '',
      position: null,
      playlist: null,
      video: null,
      favoriteUsers: null,
      user: this.tokenStorage.getUser()
    };

    this.trackService.addTrack(newTrack).subscribe(
      response => {
        this.track = response;
      },
      error => {
        alert('An error with adding new track has occurred');
      }
    );
  }

}
