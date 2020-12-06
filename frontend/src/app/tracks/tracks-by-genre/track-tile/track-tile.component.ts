import {Component, ElementRef, Input, OnInit, ViewChild} from '@angular/core';
import {Track} from '../../track/model/track';
import {DomSanitizer, SafeResourceUrl} from '@angular/platform-browser';

@Component({
  selector: 'app-track-tile',
  templateUrl: './track-tile.component.html',
  styleUrls: ['./track-tile.component.css']
})
export class TrackTileComponent implements OnInit {


  @Input() tracks: Array<Track>;
  @Input() totalNumberOfTracks: number;
  @Input() totalNumberOfPages: number;
  @Input() currentPage: number;
  @Input() isLoggedIn: boolean;

  mapa: Map<number, SafeResourceUrl> = new Map<number, SafeResourceUrl>();
  divShowMapa: Map<number, boolean> = new Map<number, boolean>();
  track: Track;
  url = 'https://i.ibb.co/JrDVPRN/Untitled.png';
  urlSafe: SafeResourceUrl;

  divShow = true;

  constructor(public sanitizer: DomSanitizer) {}

  fakeClick(trackId: number) {
    this.divShowMapa.set(trackId, true);
    this.track = this.tracks.find(x => x.id === trackId);
    this.mapa.set(this.track.id, this.track.safeUrl);
  }

  ngOnInit() {
    this.urlSafe = this.sanitizer.bypassSecurityTrustResourceUrl(this.url);
    this.setDefaultPlayer();
  }

  setDefaultPlayer() {
    this.tracks.forEach(t => {
      this.mapa.set(t.id, this.sanitizer.bypassSecurityTrustResourceUrl(this.url));
    });
  }

}
