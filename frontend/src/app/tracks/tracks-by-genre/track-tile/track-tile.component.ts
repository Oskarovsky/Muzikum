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
  track: Track;
  url = 'https://i.ibb.co/JrDVPRN/Untitled.png';
  urlSafe: SafeResourceUrl;

  constructor(public sanitizer: DomSanitizer) {}

  fakeClick(trackId: number) {
    this.track = this.tracks.find(x => x.id === trackId);
    this.mapa.set(this.track.id, this.track.safeUrl);
  }

  ngOnInit() {
    this.urlSafe = this.sanitizer.bypassSecurityTrustResourceUrl(this.url);
  }

}
