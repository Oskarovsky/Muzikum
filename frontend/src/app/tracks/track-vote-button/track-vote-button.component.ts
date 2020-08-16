import {Component, Input, OnInit} from '@angular/core';
import {Track} from '../track/model/track';

@Component({
  selector: 'app-track-vote-button',
  templateUrl: './track-vote-button.component.html',
  styleUrls: ['./track-vote-button.component.css']
})
export class TrackVoteButtonComponent implements OnInit {

  @Input() track: Track;

  constructor() { }

  ngOnInit() {
  }

}
