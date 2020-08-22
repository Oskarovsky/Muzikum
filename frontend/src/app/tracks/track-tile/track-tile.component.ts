import {Component, Input, OnInit} from '@angular/core';
import {Track} from '../track/model/track';
import { SafeResourceUrl, DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'app-track-tile',
  templateUrl: './track-tile.component.html',
  styleUrls: ['./track-tile.component.css']
})
export class TrackTileComponent implements OnInit {

  @Input() data: Array<Track>;
  @Input() mapUrl = new Map();

  safeSrc: SafeResourceUrl;
  constructor(private sanitizer: DomSanitizer) {
    this.safeSrc =  this.sanitizer.bypassSecurityTrustResourceUrl(
      'https://krakenfiles.com/getEmbedPlayer/1f02d877ff?width=450&autoplay=false&date=18-08-2020');
  }
  ngOnInit() {
    this.migrateUrl();
  }

  migrateUrl() {
    this.data.forEach( track => {
      this.mapUrl.set(track.id, this.sanitizer.bypassSecurityTrustResourceUrl(track.urlPlugin));
    });
  }

}
