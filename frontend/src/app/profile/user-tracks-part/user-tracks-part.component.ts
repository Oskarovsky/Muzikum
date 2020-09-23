import { Component, OnInit } from '@angular/core';
import {TrackService} from '../../services/track/track.service';
import {ActivatedRoute} from '@angular/router';
import {DomSanitizer} from '@angular/platform-browser';
import {Subscription} from 'rxjs';
import {Track} from '../../tracks/track/model/track';
import {TokenStorageService} from '../../services/auth/token-storage.service';

@Component({
  selector: 'app-user-tracks-part',
  templateUrl: './user-tracks-part.component.html',
  styleUrls: ['./user-tracks-part.component.scss']
})
export class UserTracksPartComponent implements OnInit {

  sub: Subscription;

  tracks: Track[];
  totalNumberOfTracks: number;
  totalNumberOfPages;
  numberOfPage: number;
  currentPage: number;
  currentUser: any;
  isLoggedIn = false;




  constructor(private trackService: TrackService,
              private route: ActivatedRoute,
              private tokenStorage: TokenStorageService,
              private sanitizer: DomSanitizer) {
    this.sub = this.route.params.subscribe(params => {
      this.currentPage = params.page || 1;
    });
  }

  ngOnInit() {
    if (this.tokenStorage.getToken()) {
      this.isLoggedIn = true;
      this.currentUser = this.tokenStorage.getUser();
    }
  }
}
