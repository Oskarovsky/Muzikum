import { Component, OnInit } from '@angular/core';
import {Video} from '../video/model/video';
import {Track} from '../../tracks/track/model/track';
import {VideoService} from '../../services/video/video.service';
import {DomSanitizer} from '@angular/platform-browser';
import {TrackService} from '../../services/track/track.service';
import {Subscription} from 'rxjs';
import {ActivatedRoute} from '@angular/router';
import {Playlist} from '../../playlists/playlist/model/playlist';
import {TokenStorageService} from '../../services/auth/token-storage.service';

@Component({
  selector: 'app-video-details',
  templateUrl: './video-details.component.html',
  styleUrls: ['./video-details.component.css']
})
export class VideoDetailsComponent implements OnInit {

  video: Video;
  playlist: Playlist;
  tracks: Track[];
  sub: Subscription;
  showAdminBoard = false;
  private roles: string[];
  isLoggedIn = false;



  constructor(private videoService: VideoService,
              private tokenStorage: TokenStorageService,
              private trackService: TrackService,
              private route: ActivatedRoute,
              private sanitizer: DomSanitizer) {}

  ngOnInit() {
    if (this.tokenStorage.getToken()) {
      this.isLoggedIn = true;
      this.roles = this.tokenStorage.getUser().roles;
      this.showAdminBoard = this.roles.includes('ROLE_ADMIN');
    }
    this.getVideoById();
    this.getAllTracksFromVideo();
  }

  public getVideoById() {
    this.sub = this.route.params.subscribe(params => {
      const id = params.id;
      if (id) {
        this.videoService.getVideo(id).subscribe(
          response => {
            this.video = response;
            this.secureAllUrl(this.video);
          },
          error => {
            alert('An error with fetching video has occurred');
          });
      }
    });
  }

  public getPlaylistFormVideo() {
    this.sub = this.route.params.subscribe(params => {
      const id = params.id;
      if (id) {
        this.videoService.getPlaylistFromVideo(id).subscribe(
          response => {
            this.playlist = response;
          },
          error => {
            alert('An error with fetching playlist for current video');
          }
        );
      }
    });
  }

  public getAllTracksFromVideo() {
    this.sub = this.route.params.subscribe(params => {
      const id = params.id;
      if (id) {
        this.videoService.getAllTracksFromVideo(id).subscribe(
          response => {
            this.tracks = response;
          },
          error => {
            alert('An error with fetching tracks has occurred');
          }
        );
      }
    });
  }

  secureAllUrl(video: Video) {
    video.safeUrl = this.sanitizer.bypassSecurityTrustResourceUrl(`https://www.youtube.com/embed/${video.url}`);
  }

}
