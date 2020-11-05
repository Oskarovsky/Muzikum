import { Component, OnInit, } from '@angular/core';
import { TokenStorageService } from '../services/auth/token-storage.service';
import { Observable, Observer } from 'rxjs';
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
  numberOfTracks: any;

  fileInfos: Observable<any>;
  base64Image: any;
  imageUrl: any;

  constructor(private tokenStorage: TokenStorageService,
              private http: HttpClient,
              private uploadService: UploadFileService,
              private favoriteService: FavoriteService,
              private trackService: TrackService) { }

  ngOnInit() {
    this.currentUser = this.tokenStorage.getUser();
    this.fileInfos = this.uploadService.getFile(this.currentUser.username);


/*    if (this.imageUrl != null) {
      this.getBase64ImageFromURL(this.imageUrl).subscribe(base64data => {
        this.base64Image = 'data:image/jpg;base64,' + base64data;
      });
    }*/

    // this.getNumberOfTracksAddedByTheUser(this.currentUser.username);
    this.getImageFromService();
    this.getLastAddedTracksByUsername(this.currentUser.username, 5);
    this.getAllFavoritesTracksByUser(this.currentUser.username);
  }

  selectFile(event) {
    this.selectedFile = event.target.files;
  }

/*  getBase64ImageFromURL(url: string) {
    return Observable.create((observer: Observer<string>) => {
      const img = new Image();
      img.crossOrigin = 'Anonymous';
      img.src = url;  img.src = url;
      if (!img.complete) {
        img.onload = () => {
          observer.next(this.getBase64Image(img));
          observer.complete();
        };
        img.onerror = (err) => {
          observer.error(err);
        };
      } else {
        observer.next(this.getBase64Image(img));
        observer.complete();
      }
    });
  }

  getBase64Image(img: HTMLImageElement) {
    const canvas = document.createElement('canvas');
    canvas.width = img.width;
    canvas.height = img.height;
    const ctx = canvas.getContext('2d');
    ctx.drawImage(img, 0, 0);
    const dataURL = canvas.toDataURL('image/png');
    console.log(dataURL);
    return dataURL.replace(/^data:image\/(png|jpg);base64,/, '');
  }*/

  createImageFromBlob(image: Blob) {
    const reader = new FileReader();
    reader.addEventListener('load', () => {
      this.imageToShow = reader.result;
    }, false);

    if (image) {
      reader.readAsDataURL(image);
    }
  }

  getImageUrl() {
    this.uploadService.getImageUrl(this.currentUser.id).subscribe(data => {
      this.imageUrl = data;
    }, error => {
      console.log(error);
    });
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

  // public getNumberOfTracksAddedByTheUser(username: string) {
  //   this.trackService.getNumberOfTracksAddedByTheUser(username).subscribe(
  //     response => {
  //       this.numberOfTracks = response;
  //     },
  //     error => {
  //       alert('An error has occurred while fetching tracks');
  //     }
  //   );
  // }




}
