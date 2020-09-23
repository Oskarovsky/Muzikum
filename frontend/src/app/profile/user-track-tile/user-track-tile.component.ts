import {Component, Input, OnInit} from '@angular/core';
import {Track} from '../../tracks/track/model/track';

@Component({
  selector: 'app-user-track-tile',
  templateUrl: './user-track-tile.component.html',
  styleUrls: ['./user-track-tile.component.css']
})
export class UserTrackTileComponent implements OnInit {

  @Input() tracks: Array<Track>;
  @Input() totalNumberOfTracks: number;
  @Input() totalNumberOfPages: number;
  @Input() currentPage: number;

  constructor() { }

  ngOnInit() {
  }

}
