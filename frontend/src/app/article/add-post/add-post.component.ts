import { Component, OnInit } from '@angular/core';
import { Post } from '../model/post';
import {Playlist} from '../../playlists/playlist/model/playlist';
import {User} from '../../services/user/user';
import {Comment} from '../model/comment';
import {PostService} from '../../services/article/post.service';
import {CommentService} from '../../services/article/comment.service';
import {TokenStorageService} from '../../services/auth/token-storage.service';

@Component({
  selector: 'app-add-post',
  templateUrl: './add-post.component.html',
  styleUrls: ['./add-post.component.css']
})
export class AddPostComponent implements OnInit {

  posts: Post[] = [];
  comments: Comment[] = [];
  isUserLogged = false;
  user: null;

  modelPost: Post = {
    id: null,
    title: '',
    description: '',
    content: '',
    user: null
  };

  modelUser: User = {
    id: null,
    username: '',
    email: '',
    password: ''
  };


  constructor(private postService: PostService,
              private commentService: CommentService,
              private tokenStorageService: TokenStorageService) { }

  ngOnInit() {
    this.isUserLogged = !!this.tokenStorageService.getToken();
    if (this.isUserLogged) {
      this.modelUser.username = this.tokenStorageService.getUser().username;
      this.modelUser.id = this.tokenStorageService.getUser().id;
      this.modelUser.email = this.tokenStorageService.getUser().email;
      this.modelUser.password = this.tokenStorageService.getUser().password;
    }
    this.getAllPosts();
  }

  public getAllPosts() {
    this.postService.getAllPosts().subscribe(
      res => {
        this.posts = res;
      },
      error => {
        alert('An error with fetching posts has occurred');
      }
    );
  }

  createPost(title: string, description: string, content: string) {
    const newPost: Post = {
      id: null,
      title,
      description,
      content,
      user: this.modelUser
    };
    this.postService.addPost(newPost).subscribe(
      result => {
        newPost.title = title;
        newPost.description = description;
        newPost.content = content;
        this.posts.push(newPost);
      },
      error => {
        alert('An error has occurred while saving the post');
      }
    );
  }
}
