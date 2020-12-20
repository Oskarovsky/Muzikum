import { Component, OnInit } from '@angular/core';
import {VideoService} from '../../services/video/video.service';
import {AlertService} from '../../services/alert/alert.service';

@Component({
  selector: 'app-video-all',
  templateUrl: './video-all.component.html',
  styleUrls: ['./video-all.component.scss']
})
export class VideoAllComponent implements OnInit {

  videoList: Array<any>;

  constructor(private videoService: VideoService,
              private alertService: AlertService) {}

  ngOnInit() {
    this.getAllVideos();
  }

  getAllVideos() {
    this.videoService.getAllVideosSortedByViews().subscribe(
      (video: any) => {
        this.videoList = video;
      },
      error => {
        this.alertService.error('Nie udało się statystyk dotyczących video');
      }
    );
  }
}
