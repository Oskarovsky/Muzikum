import { Component, OnInit } from '@angular/core';
import {Track} from '../track/model/track';
import {TrackService} from '../../services/track/track.service';

@Component({
  selector: 'app-tracks-part',
  templateUrl: './tracks-part.component.html',
  styleUrls: ['./tracks-part.component.css']
})
export class TracksPartComponent implements OnInit {

  tracks$: Array<Track> = [];

  constructor(private trackService: TrackService) {
    this.trackService.getAllTracks().subscribe(track => {
      this.tracks$ = track;
      console.log(track[0].title);
      console.log('XXXXX');
    });
  }

  ngOnInit() {
  }

}
