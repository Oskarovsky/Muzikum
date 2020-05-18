import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Playlist } from '../../playlists/playlist/model/playlist';
import { Track } from 'src/app/tracks/track/model/track';
import {environment} from '../../../environments/environment';
import {Post} from '../../article/model/post';

const API: string = environment.serverUrl;
const POST_API = API + '/posts';

@Injectable({providedIn: 'root'})
export class PostService {

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private http: HttpClient) { }

  /** GET all posts */
  getAllPosts(): Observable<Post[]> {
    return this.http.get<Post[]>(POST_API + '/all');
  }

  /** GET all posts by User name */
  getAllPostsByUsername(username: string): Observable<Post[]> {
    return this.http.get<Post[]>(POST_API + '/user/' + username);
  }

  /** GET all posts by User name */
  getAllPostsByUserId(userId: string): Observable<Post[]> {
    return this.http.get<Post[]>(POST_API + '/userId/' + userId);
  }

  /** GET post by id */
  getPostById(id: string): Observable<any> {
    return this.http.get<Playlist>(POST_API + '/' + id);
  }

  /** GET last added posts */
  getLastAddedPosts(numberOfPosts: number): Observable<Post[]> {
    return this.http.get<Post[]>(POST_API + '/lastAdded/' + numberOfPosts);
  }

  /** POST add new post */
  addPost(post: Post): Observable<Post> {
    return this.http.post<Post>(POST_API  + '/new', post);
  }

  /** UPDATE post by id */
  updatePost(postId: string, post: Post): Observable<any> {
    return this.http.put(POST_API + '/' + postId, post);
  }

  /** DELETE post by id */
  deletePost(id: number): Observable<any> {
    return this.http.delete<Playlist>(POST_API + '/' + id);
  }
}
