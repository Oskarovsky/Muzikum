import { Component, OnInit, } from '@angular/core';
import { TokenStorageService } from '../services/auth/token-storage.service';
import { Observable, Observer } from 'rxjs';
import {UploadFileService} from '../services/storage/upload-file.service';
import { HttpEventType, HttpResponse, HttpClient } from '@angular/common/http';
import {Track} from '../tracks/track/model/track';
import {TrackService} from '../services/track/track.service';
import {FavoriteService} from '../services/favorite/favorite.service';
import {DomSanitizer} from '@angular/platform-browser';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  currentUser: any;
  selectedFile;
  currentFile: File;
  message = '';
  imageToShow: any;
  tracks: Array<any>;
  favoriteTracksByUser: Track[] = [];

  fileInfos: Observable<any>;
  imageUrl: any;

  constructor(private tokenStorage: TokenStorageService,
              private http: HttpClient,
              private uploadService: UploadFileService,
              private favoriteService: FavoriteService,
              private trackService: TrackService,
              private sanitizer: DomSanitizer) { }

  ngOnInit() {
    if (this.tokenStorage.getToken()) {
      this.currentUser = this.tokenStorage.getUser();
      this.getLastAddedTracksByUsername(this.currentUser.username, 5);
      this.getAllFavoritesTracksByUser(this.currentUser.username);
      this.getImageFromService(this.currentUser.username);
    }
  }

  getImageFromService(username: string) {
    this.uploadService.getFile(username).subscribe(
      data => {
        this.createImageFromBlob(data);
      }, error => {
        console.log(error);
      });
  }

  createImageFromBlob(image: Blob) {
    const reader = new FileReader();
    reader.addEventListener('load', () => {
      this.imageToShow = this.sanitizer.bypassSecurityTrustResourceUrl(reader.result as string);
    }, false);

    if (image) {
      reader.readAsDataURL(image);
    }
  }

  selectFile(event) {
    this.selectedFile = event.target.files;
  }

  upload() {
    this.currentFile = this.selectedFile.item(0);
    this.uploadService.upload(this.currentFile, this.currentUser.username).subscribe(
      event => {
        if (event.type === HttpEventType.UploadProgress) {
        } else if (event instanceof HttpResponse) {
          this.message = event.body.message;
        }
      },
      err => {
        this.message = 'Could not upload the file!';
        this.currentFile = undefined;
      });

    this.selectedFile = undefined;
  }

  public getLastAddedTracksByUsername(username: string, numberOfTracks: number) {
    this.trackService.getLastAddedTracksByUsername(username, numberOfTracks).subscribe(
      response => {
        this.tracks = response;
      },
      error => {
        alert('An error has occurred while fetching tracks');
      }
    );
  }

  getAllFavoritesTracksByUser(username: string) {
    this.favoriteService.getAllFavoritesTracksByUsername(username).subscribe((track: any) => {
      this.favoriteTracksByUser = track;
    });
  }

}
