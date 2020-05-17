import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import {Post} from '../../../article/model/post';
import {Subscription} from 'rxjs';
import {PostService} from '../../../services/article/post.service';
import {TokenStorageService} from '../../../services/auth/token-storage.service';
import {ActivatedRoute} from '@angular/router';
import { MatPaginator } from '@angular/material/paginator';
import {HttpClient} from '@angular/common/http';
import { MatTableDataSource } from '@angular/material/table';
import {User} from '../../../services/user/user';

@Component({
  selector: 'app-article-part',
  templateUrl: './article-part.component.html',
  styleUrls: ['./article-part.component.css']
})
export class ArticlePartComponent implements AfterViewInit {

  displayedColumns: string[] = ['id', 'createdDate', 'title', 'user'];
  dataSource = new MatTableDataSource(ELEMENT_DATA);

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;

  posts: Post[];
  sub: Subscription;
  isLoggedIn = false;
  username: string;

  constructor(private postService: PostService,
              private tokenStorage: TokenStorageService,
              private route: ActivatedRoute,
              private httpClient: HttpClient) { }

  ngAfterViewInit() {
    if (this.tokenStorage.getToken()) {
      this.isLoggedIn = true;
      this.getAllPosts();
      this.dataSource.paginator = this.paginator;
/*      this.postService.getAllPosts().subscribe(
        data => {
          this.dataSource.data = data;
        }
      );
      this.dataSource.data = this.posts;
      this.dataSource.paginator = this.paginator;*/
    }
  }


  public getAllPosts() {
    this.postService.getAllPosts().subscribe(
      response => {
        this.posts = response;
      },
      error => {
        alert('An error with fetching posts has occurred');
      }
    );
  }

  deletePost(id: number) {
    if (confirm('Czy na pewno chcesz usunąć post?')) {
      this.postService.deletePost(id).subscribe(
        response => {
          this.posts.splice(id, 1);
          window.location.reload();
        },
        error => {
          alert('Could not delete post');
        }
      );
    }
  }

  applyFilter(filterValue: string) {
    filterValue = filterValue.trim();
    filterValue = filterValue.toLowerCase();
    this.dataSource.filter = filterValue;
  }

}

export class Postx {
  id: number;
  content: string;
  title: string;
  user: User;
  description: string;
  createdDate: string;
}

const ELEMENT_DATA: Postx[] = [
  {id: 1, content: 'John', title: 'Doe', user: null, description: 'xxxx', createdDate: 'xxxx'},
  {id: 2, content: 'John', title: 'Doe', user: null, description: 'xxxx', createdDate: 'xxxx'},
  {id: 3, content: 'John', title: 'Doe', user: null, description: 'xxxx', createdDate: 'xxxx'},
  {id: 4, content: 'John', title: 'Doe', user: null, description: 'xxxx', createdDate: 'xxxx'},
  {id: 6, content: 'John', title: 'Start', user: null, description: 'xxxx', createdDate: 'xxxx'},
  {id: 7, content: 'John', title: 'JavaBIg', user: null, description: 'xxxx', createdDate: 'data tu'},
  {id: 8, content: 'John', title: 'Doe', user: null, description: 'xxxx', createdDate: 'xxxx'},
  {id: 9, content: 'John', title: 'Doe', user: null, description: 'xxxx', createdDate: 'xxxx'}
];
