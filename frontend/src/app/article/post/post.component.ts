import { Component, OnInit } from '@angular/core';
import {Post} from '../add-post/model/post';
import {Subscription} from 'rxjs';
import {PlaylistService} from '../../services/playlist/playlist.service';
import {TokenStorageService} from '../../services/auth/token-storage.service';
import {ActivatedRoute} from '@angular/router';
import {PostService} from '../../services/article/post.service';

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.css']
})
export class PostComponent implements OnInit {

  post: Post;
  sub: Subscription;
  isLoggedIn = false;

  constructor(private postService: PostService,
              private tokenStorage: TokenStorageService,
              private route: ActivatedRoute) { }

  ngOnInit() {
    if (this.tokenStorage.getToken()) {
      this.isLoggedIn = true;
      this.getAllPlaylistByUsername();
    }
  }
}
