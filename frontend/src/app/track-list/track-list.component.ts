import { Component, OnInit } from '@angular/core';
import { TrackService} from "../track/track.service";

@Component({
  selector: 'app-track-list',
  templateUrl: './track-list.component.html',
  styleUrls: ['./track-list.component.css']
})
export class TrackListComponent implements OnInit {

  tracks: Array<any>;

  constructor(private trackService: TrackService) { }

  ngOnInit() {
    this.trackService.getAll().subscribe(data => {
      this.tracks = data;
    })
  }

}
