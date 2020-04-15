import { Component, OnInit } from '@angular/core';
import {Video} from '../video/model/video';
import {Track} from '../track/model/track';
import {VideoService} from '../services/video/video.service';
import {DomSanitizer} from '@angular/platform-browser';
import {TrackService} from '../services/track/track.service';

@Component({
  selector: 'app-video-details',
  templateUrl: './video-details.component.html',
  styleUrls: ['./video-details.component.css']
})
export class VideoDetailsComponent implements OnInit {

  video: Video;
  tracks: Track[];

  constructor(private videoService: VideoService,
              private trackService: TrackService,
              private sanitizer: DomSanitizer) {}

  ngOnInit() {

  }

  public getVideoById() {
    this.sub = this.route.params.subscribe(params => {
      const id = params.id;
      if (id) {
        this.playlistService.getPlaylist(id).subscribe(
          response => {
            this.playlist = response;
          },
          error => {
            alert('An error with fetching playlist has occurred')
          });
      }
    });
  }

}
