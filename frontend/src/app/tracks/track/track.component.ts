import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';

import {TrackService} from '../../services/track/track.service';
import {HttpClient} from '@angular/common/http';
import {Track} from './model/track';
import {environment} from '../../../environments/environment';
import {DomSanitizer} from '@angular/platform-browser';
import {TrackComment} from './model/track-comment';
import {User} from '../../services/user/user';
import {TokenStorageService} from '../../services/auth/token-storage.service';

const API: string = environment.serverUrl;
const VIDEO_API = API + '/video';
const TRACK_API = API + '/tracks';
const PROVIDER_API = API + '/providers';

@Component({
  selector: 'app-track',
  templateUrl: './track.component.html',
  styleUrls: ['./track.component.css']
})
export class TrackComponent implements OnInit {

  track: Track;
  newTrackComment: TrackComment;
  trackComments: TrackComment[] = [];
  tracks: Track[] = [];
  isLoggedIn = false;

  sub: Subscription;
  trackId: number;

  modelTrackComment: TrackComment = {
    id: null,
    text: '',
    track: null,
    user: null,
    createdAt: ''
  };

  modelUser: User = {
    id: null,
    username: '',
    email: '',
    password: '',
    createdAt: '',
    favoriteTracks: null
  };

  constructor(private trackService: TrackService,
              private tokenStorage: TokenStorageService,
              private route: ActivatedRoute,
              private router: Router,
              private sanitizer: DomSanitizer,
              private http: HttpClient) {

    this.sub = this.route.params.subscribe(params => {
      this.trackId = params.id;
    });

    this.trackService.getTrackById(this.trackId).subscribe((track: Track) => {
      this.track = track;
      this.secureUrl(track);
    },
      error => {
        alert('An error has occurred while fetching track');
      });
  }

  ngOnInit() {
    this.getAllTrackComments(this.trackId);
    if (this.tokenStorage.getToken()) {
      this.isLoggedIn = true;
      this.modelUser.username = this.tokenStorage.getUser().username;
      this.modelUser.id = this.tokenStorage.getUser().id;
      this.modelUser.email = this.tokenStorage.getUser().email;
      this.modelUser.password = this.tokenStorage.getUser().password;
    }
  }

  public getAllTrackComments(trackId: number) {
    this.trackService.getAllTrackCommentsByTrackId(trackId).subscribe(
      (comments: any) => {
        this.trackComments = comments;
      },
      error => {
        alert('An error has occurred while fetching track comments');
      }
    );
  }

  public addNewTrackComment(text: string) {
    const newTrackComment: TrackComment = {
      id: null,
      text,
      track: this.track,
      user: this.modelUser,
      createdAt: ''
    };
    this.trackService.addTrackComment(newTrackComment).subscribe(
      response => {
        newTrackComment.text = text;
        this.newTrackComment = response;
      }
    );
  }

  secureUrl(track: Track) {
    track.safeUrl = this.sanitizer.bypassSecurityTrustResourceUrl(`${track.urlPlugin}`);
  }

}
