import { Component, OnInit } from '@angular/core';
import { TrackService} from '../../services/track/track.service';
import {Subscription} from 'rxjs';
import {ProviderService} from '../../services/provider/provider.service';
import {ActivatedRoute} from '@angular/router';
import {TokenStorageService} from '../../services/auth/token-storage.service';
import {FavoriteService} from '../../services/favorite/favorite.service';
import {Track} from '../track/model/track';

@Component({
  selector: 'app-track-list',
  templateUrl: './track-list.component.html',
  styleUrls: ['./track-list.component.css']
})
export class TrackListComponent implements OnInit {

  displayedColumns: string[] = ['#', 'Tytuł', 'Artysta', 'Wersja', 'Ulubione', 'Zagłosuj'];

  tracks: Array<any>;

  genres: Array<any>;

  sub: Subscription;

  isLoggedIn = false;

  favoriteTracksByUser: Track[] = [];

  favoriteTrack: Track;

  favoriteTracksIds: number[] = [];

  username: string;

  clicked  = [];


  constructor(private trackService: TrackService,
              private providerService: ProviderService,
              private tokenStorage: TokenStorageService,
              private favoriteService: FavoriteService,
              private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.isLoggedIn = !!this.tokenStorage.getToken();
    if (this.isLoggedIn) {
      this.isLoggedIn = true;
      const user = this.tokenStorage.getUser();
      this.username = user.username;
      this.getAllFavoritesTracksIdsByUser(user.username);
    }
    this.sub = this.route.params.subscribe(params => {
      const id = params.id;
      const numberOfTracks = params.numberOfTracks;
      const genre = params.genre;
      const providerName = params.providerName;
      if (genre) {
        this.trackService.getLastAddedTracksByGenre(genre, 10).subscribe((track: any) => {
          this.tracks = track;
        });
/*      } else if (id && !genre) {
        this.trackService.getTracksByProviderId(id).subscribe((track: any) => {
          this.tracks = track;
        });
      } else if (id && genre) {
        this.trackService.getTracksFromProviderByGenre(id, genre).subscribe((track: any) => {
          this.tracks = track;
        });
      } else if (!id && genre) {
        this.trackService.getTracksByGenre(genre).subscribe((track: any) => {
          this.tracks = track;
        });
      } else if (providerName && !id && !genre) {
        this.trackService.getTracksByProviderName(providerName).subscribe((track: any) => {
          this.tracks = track;
        });*/
      }
    });
  }

  getAllFavoritesTracksIdsByUser(username: string) {
    this.favoriteService.getAllFavoritesTracksIdsByUsername(username).subscribe((id: any) => {
      this.favoriteTracksIds = id;
    });
  }

  addTrackToFavorites(id: number, username: string) {
    this.trackService.addTrackToFavorites(id, username).subscribe((data: any) => {
      this.favoriteTrack = data;
    });
  }
}
