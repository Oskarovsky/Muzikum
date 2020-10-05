import { Component, OnInit } from '@angular/core';
import { TokenStorageService } from '../services/auth/token-storage.service';
import { Observable } from 'rxjs';
import {UploadFileService} from '../services/storage/upload-file.service';
import { HttpEventType, HttpResponse, HttpClient } from '@angular/common/http';
import {Track} from '../tracks/track/model/track';
import {TrackService} from '../services/track/track.service';
import {FavoriteService} from '../services/favorite/favorite.service';

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

  constructor(private token: TokenStorageService,
              private uploadService: UploadFileService,
              private favoriteService: FavoriteService,
              private trackService: TrackService) { }

  ngOnInit() {
    this.currentUser = this.token.getUser();
    this.fileInfos = this.uploadService.getFile(this.currentUser.username);
    this.getImageFromService();
    this.getLastAddedTracksByUsername(this.currentUser.username, 5);
    this.getAllFavoritesTracksByUser(this.currentUser.username);
  }

  selectFile(event) {
    this.selectedFile = event.target.files;
  }

  createImageFromBlob(image: Blob) {
    const reader = new FileReader();
    reader.addEventListener('load', () => {
      this.imageToShow = reader.result;
    }, false);

    if (image) {
      reader.readAsDataURL(image);
    }
  }

  getImageFromService() {
    this.uploadService.getFile(this.currentUser.username).subscribe(data => {
      this.createImageFromBlob(data);
    }, error => {
      console.log(error);
    });
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
