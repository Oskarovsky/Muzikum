import {Component, ElementRef, Input, OnInit, ViewChild} from '@angular/core';
import {Track} from '../../track/model/track';

@Component({
  selector: 'app-track-tile',
  templateUrl: './track-tile.component.html',
  styleUrls: ['./track-tile.component.css']
})
export class TrackTileComponent implements OnInit {

  @Input() tracks: Array<Track>;
  @Input() totalNumberOfTracks: number;
  @Input() totalNumberOfPages: number;
  @Input() currentPage: number;
  @Input() isLoggedIn: boolean;

  element: ElementRef;

  constructor() {}

  ngOnInit() {
  }

}
