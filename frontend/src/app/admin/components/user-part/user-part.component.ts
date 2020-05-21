import { Component, OnInit } from '@angular/core';
import {PlaylistService} from '../../../services/playlist/playlist.service';
import {TrackService} from '../../../services/track/track.service';
import {UserService} from '../../../services/user/user.service';
import {TokenStorageService} from '../../../services/auth/token-storage.service';
import {ActivatedRoute} from '@angular/router';
import {User} from '../../../services/user/user';


@Component({
  selector: 'app-user-part',
  templateUrl: './user-part.component.html',
  styleUrls: ['./user-part.component.css']
})
export class UserPartComponent implements OnInit {

  users: User[] = [];
  searchText: string;

  constructor(private playlistService: PlaylistService,
              private trackService: TrackService,
              private userService: UserService,
              private tokenStorage: TokenStorageService,
              private route: ActivatedRoute) { }

  ngOnInit() {
    if (this.tokenStorage.getToken()) {
      this.getLastAddedUsers('10');
    }
  }

  public getLastAddedUsers(numberOfUsers: string) {
    this.userService.getLastAddedUsers(numberOfUsers).subscribe(
      response => {
        this.users = response;
      },
      error => {
        alert('An error with fetching last added users has occurred');
      }
    );
  }


}
