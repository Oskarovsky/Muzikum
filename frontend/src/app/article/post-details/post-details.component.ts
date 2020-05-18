import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {PostService} from '../../services/article/post.service';
import {Post} from '../model/post';
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-post-details',
  templateUrl: './post-details.component.html',
  styleUrls: ['./post-details.component.css']
})
export class PostDetailsComponent implements OnInit {

  post: Post;
  sub: Subscription;

  constructor(private route: ActivatedRoute,
              private postService: PostService) { }

  ngOnInit() {
    this.getPostById();
  }

  public getPostById() {
    this.sub = this.route.params.subscribe(params => {
      const postId = params.id;
      if (postId) {
        this.postService.getPostById(postId).subscribe(
          response => {
            this.post = response;
          },
          error => {
            alert('An error with fetching post by id has occurred');
          }
        );
      }
    });
  }
}
