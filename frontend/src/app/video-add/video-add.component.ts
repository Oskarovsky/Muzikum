import { Component, OnInit } from '@angular/core';
import { Video } from '../video/model/video';
import { Track } from '../track/model/track';

@Component({
  selector: 'app-video-add',
  templateUrl: './video-add.component.html',
  styleUrls: ['./video-add.component.css']
})
export class VideoAddComponent implements OnInit {

  videos: Video[];

  tracks: Track[];

  modelVideo: Video = {
    id: null,
    name: '',
    url: '',
    tracks: null
  };

  constructor() { }

  ngOnInit() {
  }

}
