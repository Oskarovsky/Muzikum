import { Component, OnInit } from '@angular/core';
import {VideoService} from '../services/video/video.service';
import {Video} from './model/video';
import {Track} from '../track/model/track';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'app-video',
  templateUrl: './video.component.html',
  styleUrls: ['./video.component.css']
})
export class VideoComponent implements OnInit {

  videos: Video[];
  tracks: Track[];
  searchText: string;
  safeVideoUrl: any;

  constructor(private videoService: VideoService,
              private sanitizer: DomSanitizer) {}

  ngOnInit() {
    this.getAllVideo();
  }

  public getAllVideo() {
    this.videoService.getAllVideos().subscribe(
      response => {
        this.videos = response;
        this.secureAllUrl(this.videos);
      },
      error => {
        alert('An error with fetching videos has occurred');
      }
    );
  }

  secureAllUrl(allVideos: Video[]) {
    for (const video of allVideos) {
      video.safeUrl = this.sanitizer.bypassSecurityTrustResourceUrl(`https://www.youtube.com/embed/${video.url}`);
    }
  }

}
