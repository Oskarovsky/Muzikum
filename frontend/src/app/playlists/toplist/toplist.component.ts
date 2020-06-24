import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Subscription} from 'rxjs';
import {TrackService} from '../../services/track/track.service';
import {ProviderService} from '../../services/provider/provider.service';
import {TokenStorageService} from '../../services/auth/token-storage.service';

@Component({
  selector: 'app-toplist',
  templateUrl: './toplist.component.html',
  styleUrls: ['./toplist.component.css']
})
export class ToplistComponent implements OnInit {

  tracks: Array<any>;
  genres: Array<any>;
  sub: Subscription;
  isLoggedIn = false;

  constructor(private trackService: TrackService,
              private providerService: ProviderService,
              private tokenStorage: TokenStorageService,
              private route: ActivatedRoute) {
  }

  ngOnInit() {
    if (this.tokenStorage.getToken()) {
      this.isLoggedIn = true;
    }

    this.sub = this.route.params.subscribe(params => {
      const genre = params.genre;
      if (genre) {
        if (this.isLoggedIn === true) {
          this.trackService.getTopListByGenre(genre, 20).subscribe((track: any) => {
            this.tracks = track;
          });
        } else {
          this.trackService.getTopListByGenre(genre, 10).subscribe((track: any) => {
            this.tracks = track;
          });
        }
      }
    });
  }
}
