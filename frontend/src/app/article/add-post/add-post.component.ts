import {Component, OnInit, ViewChild} from '@angular/core';
import {Post} from '../model/post';
import {User} from '../../services/user/user';
import {Comment} from '../model/comment';
import {PostService} from '../../services/article/post.service';
import {CommentService} from '../../services/article/comment.service';
import {TokenStorageService} from '../../services/auth/token-storage.service';
import {Router} from '@angular/router';
import {Track} from '../../tracks/track/model/track';

@Component({
  selector: 'app-add-post',
  templateUrl: './add-post.component.html',
  styleUrls: ['./add-post.component.css']
})
export class AddPostComponent implements OnInit {

  name = 'ng2-ckeditor';
  ckeConfigForDescription: any;
  ckeConfigForContent: any;
  content: string;
  log: string;
  res: any;
  @ViewChild('Post', {static: false}) Post: any;

  posts: Post[] = [];
  comments: Comment[] = [];
  isUserLogged = false;
  user: null;
  post = new Post();
  isAdmin = false;

  modelPost: Post = {
    id: null,
    title: '',
    description: '',
    content: '',
    user: null,
    createdAt: null
  };

  modelUser: User = {
    id: null,
    username: '',
    email: '',
    password: '',
    createdAt: '',
    favoriteTracks: null,
    imageUrl: '',
    provider: '',
    providerId: null
  };


  constructor(private postService: PostService,
              private commentService: CommentService,
              private tokenStorageService: TokenStorageService,
              private router: Router) { }

  ngOnInit() {
    this.isUserLogged = !!this.tokenStorageService.getToken();
    if (this.isUserLogged) {
      this.isAdmin = this.tokenStorageService.getUser().roles.includes('ROLE_ADMIN');
      this.modelUser.username = this.tokenStorageService.getUser().username;
      this.modelUser.id = this.tokenStorageService.getUser().id;
      this.modelUser.email = this.tokenStorageService.getUser().email;
      this.modelUser.password = this.tokenStorageService.getUser().password;
      this.modelUser.provider = this.tokenStorageService.getUser().provider;
      this.modelUser.imageUrl = this.tokenStorageService.getUser().imageUrl;

      if (!this.isAdmin) {
        this.redirect();
      }
    } else {
      this.redirect();
    }
    this.getAllPosts();
    this.ckeConfigForDescription = {
      allowedContent: false,
      // extraPlugins: 'divarea',
      forcePasteAsPlainText: true,
      height: 100,
    };
    this.ckeConfigForContent = {
      allowedContent: false,
      // extraPlugins: 'divarea',
      forcePasteAsPlainText: true,
      height: 350,
    };
  }

  onSubmit() {
    this.postService.addPost(this.post).subscribe(
      (data: any) => {
        alert('Data saved successfully');
      }
    );

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
      user: this.modelUser,
      createdAt: null
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

  redirect() {
    this.router.navigate(['/']);
  }
}
