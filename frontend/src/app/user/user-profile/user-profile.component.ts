import { Component, OnInit } from '@angular/core';
import {Track} from '../../tracks/track/model/track';
import {TokenStorageService} from '../../services/auth/token-storage.service';
import {UploadFileService} from '../../services/storage/upload-file.service';
import {FavoriteService} from '../../services/favorite/favorite.service';
import {TrackService} from '../../services/track/track.service';
import {Subscription} from 'rxjs';
import {ActivatedRoute} from '@angular/router';
import {UserService} from '../../services/user/user.service';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.scss']
})
export class UserProfileComponent implements OnInit {

  currentUser: any;
  sub: Subscription;
  message = '';
  userAvatar: any;
  imageToShow: any;
  isImageLoading: any;
  tracks: Array<any>;
  favoriteTracksByUser: Track[] = [];
  userProfile: any;

  constructor(private token: TokenStorageService,
              private uploadService: UploadFileService,
              private favoriteService: FavoriteService,
              private route: ActivatedRoute,
              private userService: UserService,
              private trackService: TrackService) { }

  ngOnInit() {
    this.currentUser = this.token.getUser();
    this.getUserProfile();
    this.getImageFromService();
    this.getLastAddedTracksByUsername(this.userProfile.username, 5);
    this.getAllFavoritesTracksByUser(this.userProfile.username);
  }

  getUserProfile() {
    this.sub = this.route.params.subscribe(params => {
      const username = params.username;
      if (username) {
        this.userService.getUserByUsername(username).subscribe(
          response => {
            this.userProfile = response;
          },
          error => {
            alert('An error with fetching user by username has occurred');
          }
        );
      }
    });
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
    this.isImageLoading = true;
    this.uploadService.getFile(this.currentUser.username).subscribe(data => {
      this.createImageFromBlob(data);
    }, error => {
      this.isImageLoading = false;
      console.log(error);
    });
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
