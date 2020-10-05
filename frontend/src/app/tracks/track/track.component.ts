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
import {UploadFileService} from '../../services/storage/upload-file.service';

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
  currentUser: any;

  sub: Subscription;
  trackId: number;
  imagesToShow: Map<string, any> = new Map<string, any>();


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
              private http: HttpClient,
              private uploadFileService: UploadFileService) {

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
    this.getUserImage(this.modelUser.username);
  }

  public getAllTrackComments(trackId: number) {
    this.trackService.getAllTrackCommentsByTrackId(trackId).subscribe(
      (comments: any) => {
        this.trackComments = comments;
        for (const comment of comments) {
          this.getUserImage(comment.user.username);
        }
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
        window.location.reload();
      },
      error => {
        alert('An error has occurred while adding comment to track');
      }
    );
  }

  getUserImage(username: string) {
    this.uploadFileService.getFile(username).subscribe(data => {
      this.createImageFromBlob(username, data);
    }, error => {
      console.log(error);
    });
  }

  createImageFromBlob(username: string, image: Blob) {
    const reader = new FileReader();
    reader.addEventListener('load', () => {
      this.imagesToShow.set(username, reader.result);
    }, false);

    if (image) {
      reader.readAsDataURL(image);
    }
  }

  secureUrl(track: Track) {
    track.safeUrl = this.sanitizer.bypassSecurityTrustResourceUrl(`${track.urlPlugin}`);
  }

}
