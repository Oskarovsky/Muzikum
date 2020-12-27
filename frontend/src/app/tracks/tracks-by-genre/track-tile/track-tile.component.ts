import {Component, Input, OnInit, SecurityContext} from '@angular/core';
import {Track} from '../../track/model/track';
import {DomSanitizer, SafeResourceUrl} from '@angular/platform-browser';
import {UploadFileService} from '../../../services/storage/upload-file.service';

@Component({
  selector: 'app-track-tile',
  templateUrl: './track-tile.component.html',
  styleUrls: ['./track-tile.component.scss']
})
export class TrackTileComponent implements OnInit {


  @Input() tracks: Array<Track>;
  @Input() totalNumberOfTracks: number;
  @Input() totalNumberOfPages: number;
  @Input() currentPage: number;
  @Input() isLoggedIn: boolean;

  mapa: Map<number, SafeResourceUrl> = new Map<number, SafeResourceUrl>();
  divShowMapa: Map<number, boolean> = new Map<number, boolean>();
  // coverMapa: Map<>
  track: Track;

  constructor(public sanitizer: DomSanitizer,
              public fileService: UploadFileService) {}

  fakeClick(trackId: number) {
    this.divShowMapa.set(trackId, true);
    this.tracks.filter(x => x.id !== trackId).forEach(t => {
      this.mapa.set(t.id, null);
      this.divShowMapa.set(t.id, false);
    });
    this.track = this.tracks.find(x => x.id === trackId);
    this.mapa.set(this.track.id, this.track.safeUrl);
  }

  ngOnInit() {
    // this.fileService.getTrackCoverByTrackId()
  }



}
