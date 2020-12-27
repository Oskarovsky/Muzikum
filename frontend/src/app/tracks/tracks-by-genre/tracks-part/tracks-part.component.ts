import {AfterViewChecked, Component, OnInit} from '@angular/core';
import {Track} from '../../track/model/track';
import {TrackService} from '../../../services/track/track.service';
import {Subscription} from 'rxjs';
import {ActivatedRoute} from '@angular/router';
import {DomSanitizer} from '@angular/platform-browser';
import {TokenStorageService} from '../../../services/auth/token-storage.service';
import {HttpHeaders} from '@angular/common/http';
import {UploadFileService} from '../../../services/storage/upload-file.service';

const httpOptions = {
  headers: new HttpHeaders({
    // 'Content-Type': 'application/json',
    'Access-Control-Allow-Origin' : '*' })
};

@Component({
  selector: 'app-tracks-part',
  templateUrl: './tracks-part.component.html',
  styleUrls: ['./tracks-part.component.css']
})
export class TracksPartComponent implements OnInit {

  sub: Subscription;

  tracks: Track[];
  totalNumberOfTracks: number;
  totalNumberOfPages;
  numberOfPage: number;
  isLoggedIn = false;

  coversToShow: Map<number, any> = new Map<number, any>();
  genre: string;
  currentPage: number;


  constructor(private trackService: TrackService,
              private tokenStorage: TokenStorageService,
              private uploadFileService: UploadFileService,
              private route: ActivatedRoute,
              private sanitizer: DomSanitizer) {
    this.sub = this.route.params.subscribe(params => {
      this.genre = params.genre;
      this.currentPage = params.page || 1;
    });

    this.trackService.getTrackPageByGenre(this.genre, +this.currentPage - 1)
      .subscribe(trackResponse => {
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
    }
  }

  secureAllUrl(allTracks: Track[]) {
    for (const track of allTracks) {
      track.urlPlugin.replace('false', 'true');
      track.safeUrl = this.sanitizer.bypassSecurityTrustResourceUrl(`${track.urlPlugin}`);
    }
  }

  getCoverImage(trackId: number) {
    this.uploadFileService.getCoverFile(trackId).subscribe(data => {
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
