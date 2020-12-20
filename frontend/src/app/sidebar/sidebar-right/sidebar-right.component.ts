import {Component, OnInit} from '@angular/core';
import {TokenStorageService} from '../../services/auth/token-storage.service';
import {VideoService} from '../../services/video/video.service';
import {AlertService} from '../../services/alert/alert.service';

@Component({
  selector: 'app-sidebar-right',
  templateUrl: './sidebar-right.component.html',
  styleUrls: ['./sidebar-right.component.css']
})
export class SidebarRightComponent implements OnInit {

  isLoggedIn = false;
  username: string;

  videoList: Array<any>;

  constructor(private tokenStorageService: TokenStorageService,
              private videoService: VideoService,
              private alertService: AlertService) { }

  ngOnInit() {
    this.getTopVideos();
    this.isLoggedIn = !!this.tokenStorageService.getToken();
    if (this.isLoggedIn) {
      const user = this.tokenStorageService.getUser();
      this.username = user.username;
    }
  }

  getTopVideos() {
    this.videoService.getTopVideos().subscribe(
      (video: any) => {
      this.videoList = video;
    },
        error => {
      this.alertService.error('Nie udało się pobrać całej listy video');
      }
    );
  }

}
