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

    this.trackService.getTrackPageByUserUsername(this.currentUser.username, +this.currentPage - 1).subscribe(trackResponse => {
      this.totalNumberOfTracks = trackResponse.totalElements;
      this.numberOfPage = trackResponse.numberPage;
      this.totalNumberOfPages = trackResponse.totalPages;
      this.tracks = trackResponse.trackList;
      this.secureAllUrl(this.tracks);
    });
  }

  ngOnInit() {
    if (this.tokenStorage.getToken()) {
      this.isLoggedIn = true;
      this.currentUser = this.tokenStorage.getUser();
    }
  }

  secureAllUrl(allTracks: Track[]) {
    for (const track of allTracks) {
      track.safeUrl = this.sanitizer.bypassSecurityTrustResourceUrl(`${track.urlPlugin}`);
    }
  }

  // tslint:disable-next-line:variable-name
  createRange(number) {
    const items: number[] = [];
    for (let i = 1; i <= number; i++) {
      items.push(i);
    }
    return items;
  }

  nextPage(currentPage) {
    return +currentPage + 1;
  }

  previousPage(currentPage: number) {
    return currentPage - 1;
  }
}
