import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';

import {TrackService} from '../../services/track/track.service';
import {HttpClient} from '@angular/common/http';
import {Track} from './model/track';
import {environment} from '../../../environments/environment';
import {DomSanitizer} from '@angular/platform-browser';

const API: string = environment.serverUrl;
const VIDEO_API = API + '/video';
const TRACK_API = API + '/tracks';
const PROVIDER_API = API + '/providers';

@Component({
  selector: 'app-track',
  templateUrl: './track.component.html',
  styleUrls: ['./track.component.css']
})
export class TrackComponent implements OnInit {

  track: Track;
  tracks: Track[] = [];

  sub: Subscription;
  trackId: number;

  constructor(private trackService: TrackService,
              private route: ActivatedRoute,
              private router: Router,
              private sanitizer: DomSanitizer,
              private http: HttpClient) {

    this.sub = this.route.params.subscribe(params => {
      this.trackId = params.id;
    });

    this.trackService.getTrackById(this.trackId).subscribe((track: Track) => {
      this.track = track;
      this.secureUrl(track);
    },
      error => {
        alert('An error has occurred while fetching track');
      });
  }

  ngOnInit() {
  }

  public getTrackById(id: number) {
    this.trackService.getTrackById(id).subscribe(
      (track: Track) => {
      this.track = track;
    },
      error => {
        alert('An error has occurred while fetching track');
      }
    );
  }

  secureUrl(track: Track) {
    track.safeUrl = this.sanitizer.bypassSecurityTrustResourceUrl(`${track.urlPlugin}`);
  }

}
