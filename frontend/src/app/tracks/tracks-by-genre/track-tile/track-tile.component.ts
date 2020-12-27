import {AfterViewChecked, Component, ElementRef, Input, OnChanges, OnInit, SecurityContext, SimpleChanges} from '@angular/core';
import {Track} from '../../track/model/track';
import {DomSanitizer, SafeResourceUrl} from '@angular/platform-browser';
import {UploadFileService} from '../../../services/storage/upload-file.service';

@Component({
  selector: 'app-track-tile',
  templateUrl: './track-tile.component.html',
  styleUrls: ['./track-tile.component.scss']
})
export class TrackTileComponent implements AfterViewChecked {

  @Input() tracks: Array<Track>;
  @Input() totalNumberOfTracks: number;
  @Input() totalNumberOfPages: number;
  @Input() currentPage: number;
  @Input() isLoggedIn: boolean;

  coversToShow: Map<number, any> = new Map<number, any>();
  mapa: Map<number, SafeResourceUrl> = new Map<number, SafeResourceUrl>();
  divShowMapa: Map<number, boolean> = new Map<number, boolean>();
  track: Track;
  idList: Array<number> = new Array<number>();

  constructor(public sanitizer: DomSanitizer,
              public fileService: UploadFileService) {
  }

  ngAfterViewChecked() {
    this.tracks.forEach(t => {
      if (!this.idList.includes(t.id)) {
        this.idList.push(t.id);
        this.getCoverImage(t.id);
      }
    });
  }

  fakeClick(trackId: number) {
    this.divShowMapa.set(trackId, true);
    this.tracks.filter(x => x.id !== trackId).forEach(t => {
      this.mapa.set(t.id, null);
      this.divShowMapa.set(t.id, false);
    });
    this.track = this.tracks.find(x => x.id === trackId);
    this.mapa.set(this.track.id, this.track.safeUrl);
  }

  getCoverImage(trackId: number) {
    this.fileService.getCoverFile(trackId).subscribe(data => {
      this.createCoverFromBlob(trackId, data);
    }, error => {
      console.log(error);
    });
  }

  createCoverFromBlob(trackId: number, image: Blob) {
    const reader = new FileReader();
    reader.addEventListener('load', () => {
      this.coversToShow.set(trackId, this.sanitizer.bypassSecurityTrustResourceUrl(reader.result as string));
    }, false);

    if (image) {
      reader.readAsDataURL(image);
    }
  }
}
