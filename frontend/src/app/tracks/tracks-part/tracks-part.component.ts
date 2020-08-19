import { Component, OnInit } from '@angular/core';
import {Track} from '../track/model/track';
import {TrackService} from '../../services/track/track.service';
import {Subscription} from 'rxjs';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-tracks-part',
  templateUrl: './tracks-part.component.html',
  styleUrls: ['./tracks-part.component.css']
})
export class TracksPartComponent implements OnInit {

  tracks$: Array<Track> = [];
  sub: Subscription;

  tracks: Track[];

  genre: string;

  pager = {};
  pageOfItems = [];

  page = 1;
  count = 0;
  pageSize = 3;
  pageSizes = [3, 6, 9];

  constructor(private trackService: TrackService,
              private route: ActivatedRoute) {
    this.sub = this.route.params.subscribe(params => {
      this.genre = params.genre;
    });
    this.trackService.getAllTracks().subscribe(track => {
      this.tracks$ = track;
    });
  }

  ngOnInit() {
    this.route.queryParams.subscribe(x => this.getTrackOnPage( this.genre, x.page || 0));
  }

  getTrackOnPage(genre, page) {
    this.trackService.getPageTracksByGenreOnServer(genre, page).subscribe(x => {
      this.pager = x.pager;
      this.pageOfItems = x.pageOfItems;
    });
  }




}
