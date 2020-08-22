import { Component, OnInit } from '@angular/core';
import {Track} from '../track/model/track';
import {TrackService} from '../../services/track/track.service';
import {Subscription} from 'rxjs';
import {ActivatedRoute} from '@angular/router';
import {DomSanitizer} from '@angular/platform-browser';
import {Video} from '../../videos/video/model/video';

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

  urlMap = new Map();


  constructor(private trackService: TrackService,
              private route: ActivatedRoute,
              private sanitizer: DomSanitizer) {
    this.sub = this.route.params.subscribe(params => {
      this.genre = params.genre;
    });

    this.route.queryParams.subscribe(queryParams => {
      this.page = queryParams.page || 0;
    });

    this.trackService.getAllTracks().subscribe(track => {
      this.tracks$ = track;
      this.secureAllUrl(this.tracks$);
    });
  }

  ngOnInit() {
  }

  getTrackOnPage(genre, page) {
    this.trackService.getPageTracksByGenreOnServer(genre, page).subscribe(track => {
      this.tracks$ = track;
    });
  }

  secureAllUrl(allTracks: Track[]) {
    for (const track of allTracks) {
      track.safeUrl = this.sanitizer.bypassSecurityTrustResourceUrl(`${track.urlPlugin}`);
    }
  }




}
