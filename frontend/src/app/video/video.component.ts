import { Component, OnInit } from '@angular/core';
import {VideoService} from '../services/video/video.service';
import {Video} from './model/video';
import {Track} from '../track/model/track';

@Component({
  selector: 'app-video',
  templateUrl: './video.component.html',
  styleUrls: ['./video.component.css']
})
export class VideoComponent implements OnInit {

  videos: Video[];

  tracks: Track[];

  constructor(private videoService: VideoService) {}

  ngOnInit() {
    this.getAllVideo();
  }

  public getAllVideo() {
    this.videoService.getAllVideos().subscribe(
      response => {
        this.videos = response;
      },
      error => {
        alert('An error with fetching videos has occurred');
      }
    );
  }
}
