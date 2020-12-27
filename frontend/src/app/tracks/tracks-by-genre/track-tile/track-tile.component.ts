import {Component, ElementRef, Input, OnInit, SecurityContext} from '@angular/core';
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

  coversToShow: Map<number, any> = new Map<number, any>();
  mapa: Map<number, SafeResourceUrl> = new Map<number, SafeResourceUrl>();
  divShowMapa: Map<number, boolean> = new Map<number, boolean>();
  track: Track;

  constructor(public sanitizer: DomSanitizer,
              private el: ElementRef,
              public fileService: UploadFileService) {
  }

  ngOnInit() {
    console.log('userId is:', this.tracks);
    console.log('userId is:', this.totalNumberOfTracks);
    this.assignAllCovers();
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

  assignAllCovers() {
    if (this.tracks) {
      console.log('AAA = TEST');
    }
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
