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
import {AlertService} from '../../services/alert/alert.service';

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
  popularTrackRetro: Track;
  popularTrackClub: Track;
  popularTrackDance: Track;
  popularTrackVixa: Track;
  popularTrackTechno: Track;
  popularTrackDisco: Track;
  popularTrackSet: Track;
  topUploadersWeek: User[] = [];
  topUploadersMonth: User[] = [];
  topUploadersTotal: User[] = [];
  usersTrack: Track[] = [];
  isLoggedIn = false;
  username: string;

  uploaderWeekMap = new Map();
  uploaderMonthMap = new Map();
  uploaderTotalMap = new Map();

  constructor(private playlistService: PlaylistService,
              private trackService: TrackService,
              private userService: UserService,
              private alertService: AlertService,
              private tokenStorage: TokenStorageService,
              private route: ActivatedRoute) { }

  ngOnInit() {
    this.getRandomTrack();
    this.getMostPopularTrackByGenre('RETRO', 'RETRO');
    this.getMostPopularTrackByGenre('CLUB', 'CLUB');
    this.getMostPopularTrackByGenre('DANCE', 'DANCE');
    this.getMostPopularTrackByGenre('VIXA', 'VIXA');
    this.getMostPopularTrackByGenre('TECHNO', 'TECHNO');
    this.getMostPopularTrackByGenre('DISCO', 'DISCO');
    this.getMostPopularTrackByGenre('SET', 'SET');
    this.getTopUploaders('week', 5);
    this.getTopUploaders('month', 5);
    this.getTopUploaders('total', 5);
    if (this.tokenStorage.getToken()) {
      this.isLoggedIn = true;
      this.username = this.tokenStorage.getUser().username;
      this.getLastAddedTracksByUsername(this.username, 5);
    }
  }

  public getLastAddedPlaylists(numberOfPlaylists: string) {
    this.playlistService.getLastAddedPlaylists(numberOfPlaylists).subscribe(
      response => {
        this.playlists = response;
      },
      error => {
        this.alertService.error('Nie udało się pobrać ostatnio dodanych playlist');
      }
    );
  }

  public getLastAddedUsers(numberOfUsers: string) {
    this.userService.getLastAddedUsers(numberOfUsers).subscribe(
      response => {
        this.users = response;
      },
      error => {
        this.alertService.error('Nie udało się pobrać ostatnio dodanych użytkowników');
      }
    );
  }

  public getRandomTrack() {
    this.trackService.getRandomTrack().subscribe(
      response => {
        this.randomTrack = response;
      },
      error => {
        this.alertService.error('Nie udało się pobrać pobrać losowego utworu');
      }
    );
  }

  public getLastAddedTracksByUsername(username: string, numberOfTracks: number) {
    this.trackService.getLastAddedTracksByUsername(username, numberOfTracks).subscribe(
      response => {
        this.usersTrack = response;
      },
      error => {
        this.alertService.error('Nie udało się pobrać ostatnio dodanych przez Ciebie utworów');
      }
    );
  }

  public getMostPopularTrackByGenre(genre: string, option: string) {
    this.trackService.getMostPopularTrackByGenre(genre).subscribe(
      response => {
        if (option === 'RETRO') {
          this.popularTrackRetro = response;
        } else if (option === 'CLUB') {
          this.popularTrackClub = response;
        } else if (option === 'DANCE') {
          this.popularTrackDance = response;
        } else if (option === 'VIXA') {
          this.popularTrackVixa = response;
        } else if (option === 'TECHNO') {
          this.popularTrackTechno = response;
        } else if (option === 'DISCO') {
          this.popularTrackDisco = response;
        } else if (option === 'SET') {
          this.popularTrackSet = response;
        }
      },
      error => {
        this.alertService.error('Nie udało się pobrać najbardziej popularnych utworów');
      }
    );
  }

  public getTopUploaders(periodOfTime: string, numberOfUsers: number) {
    this.userService.getTopUploaders(periodOfTime, numberOfUsers).subscribe(
      response => {
        if (periodOfTime === 'week') {
          this.topUploadersWeek = response;
        } else if (periodOfTime === 'month') {
          this.topUploadersMonth = response;
        } else if (periodOfTime === 'total') {
          this.topUploadersTotal = response;
        }
        for (const uploader of response.values()) {
          this.getNumberOfTracksAddedInGivenPeriodByUsername(uploader.username, periodOfTime);
        }
      }
    );
  }

  public getNumberOfTracksAddedInGivenPeriodByUsername(username: string, periodOfTime: string) {
    this.userService.getNumberOfTracksAddedInGivenPeriodByUsername(username, periodOfTime).subscribe(
      response => {
        if (periodOfTime === 'week') {
          this.uploaderWeekMap.set(username + periodOfTime, response);
        } else if (periodOfTime === 'month') {
          this.uploaderMonthMap.set(username + periodOfTime, response);
        } else if (periodOfTime === 'total') {
          this.uploaderTotalMap.set(username + periodOfTime, response);
        }
      }
    );
  }
}
