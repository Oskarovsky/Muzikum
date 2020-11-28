import { Component, OnInit } from '@angular/core';
import { Track } from '../track/model/track';
import {Subscription} from 'rxjs';
import {TokenStorageService} from '../../services/auth/token-storage.service';
import {TrackService} from '../../services/track/track.service';
import {User} from '../../services/user/user';
import {Router} from '@angular/router';
import {AlertService} from '../../services/alert/alert.service';

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

  modelTrack: Track = {
    id: null,
    title: '',
    artist: '',
    points: null,
    genre: null,
    version: '',
    createdAt: '',
    url: '',
    urlSource: null,
    urlPlugin: '',
    safeUrl: '',
    position: null,
    playlist: null,
    video: null,
    favoriteUsers: null,
    user: null
  };

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

  genres: string[] = ['VIXA', 'CLUB', 'RETRO', 'DANCE', 'DISCO', 'TECHNO', 'MIXY/SETY'];

  urlSources: string[] = ['ZIPPYSHARE', 'KRAKENFILES'];

  constructor(private tokenStorage: TokenStorageService,
              private router: Router,
              private alertService: AlertService,
              private trackService: TrackService) { }

  ngOnInit() {
    if (this.tokenStorage.getToken()) {
      this.isLoggedIn = true;
      this.showAdminBoard = this.tokenStorage.getUser().roles.includes('ROLE_ADMIN');
      this.modelUser.username = this.tokenStorage.getUser().username;
      this.modelUser.id = this.tokenStorage.getUser().id;
      this.modelUser.email = this.tokenStorage.getUser().email;
      this.modelUser.password = this.tokenStorage.getUser().password;
      this.modelUser.provider = this.tokenStorage.getUser().provider;
      this.modelUser.imageUrl = this.tokenStorage.getUser().imageUrl;
    } else {
      this.redirect();
    }
  }

  public addNewTrack(title: string, artist: string, version: string, genre: string, url: string, urlSource: string) {
    const newTrack: Track = {
      id: null,
      title,
      artist,
      points: 0,
      genre,
      version,
      url,
      urlSource,
      urlPlugin: '',
      createdAt: '',
      safeUrl: '',
      position: null,
      playlist: null,
      video: null,
      favoriteUsers: null,
      user: this.modelUser
    };

    if (genre === 'MIXY/SETY') {
      newTrack.genre = 'SET';
    } else {
      newTrack.genre = genre;
    }

    this.trackService.addTrack(newTrack).subscribe(
      response => {
        newTrack.title = title;
        newTrack.artist = artist;
        newTrack.genre = genre;
        newTrack.version = version;
        newTrack.url = url;
        this.track = response;
        this.router.navigateByUrl('/');
        this.alertService.success('Utwór został dodany na stronę!');
      },
      error => {
        this.alertService.error('Nie udało się dodać utworu. Sprawdź wprowadzone informacje i spróubuj jeszcze raz!');
      }
    );
  }

  redirect() {
    this.router.navigate(['/']);
  }

}
