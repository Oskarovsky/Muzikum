import { Component, OnInit } from '@angular/core';
import {Track} from '../../../tracks/track/model/track';
import {Subscription} from 'rxjs';
import {PostService} from '../../../services/article/post.service';
import {TokenStorageService} from '../../../services/auth/token-storage.service';
import {ActivatedRoute} from '@angular/router';
import {HttpClient} from '@angular/common/http';
import {TrackService} from '../../../services/track/track.service';

@Component({
  selector: 'app-track-part',
  templateUrl: './track-part.component.html',
  styleUrls: ['./track-part.component.scss']
})
export class TrackPartComponent implements OnInit {

  tracks: Track[] = [];
  sub: Subscription;
  isLoggedIn = false;
  username: string;
  genres: string[] = ['CLUB', 'RETRO', 'DANCE', 'HOUSE', 'TECHNO'];

  constructor(private trackService: TrackService,
              private tokenStorage: TokenStorageService,
              private route: ActivatedRoute,
              private httpClient: HttpClient) { }

  ngOnInit() {
    if (this.tokenStorage.getToken()) {
      this.isLoggedIn = true;
      this.username = this.tokenStorage.getUser().username;
      this.getLastAddedTracksByGenreOnlyWithUser('CLUB', 5);
      this.getLastAddedTracksByGenreOnlyWithUser('RETRO', 5);
      this.getLastAddedTracksByGenreOnlyWithUser('DANCE', 5);
      this.getLastAddedTracksByGenreOnlyWithUser('HOUSE', 5);
      this.getLastAddedTracksByGenreOnlyWithUser('TECHNO', 5);
    }
  }

  public getLastAddedTracksByGenreOnlyWithUser(genre: string, numberOfTracks: number) {
    this.trackService.getLastAddedTracksByGenreOnlyWithUser(genre, numberOfTracks).subscribe(
      (response: any) => {
        this.tracks = this.tracks.concat(response);
      },
      error => {
        alert('An error with fetching tracks has occurred');
      }
    );
  }

}
