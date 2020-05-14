import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Playlist } from '../../playlists/playlist/model/playlist';
import { Track } from 'src/app/tracks/track/model/track';
import {environment} from '../../../environments/environment';
import {Post} from '../../article/model/post';
import {Comment} from '../../article/model/comment';

const API: string = environment.serverUrl;
const COMMENT_POST_API = API + '/posts';

@Injectable({providedIn: 'root'})
export class CommentService {

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private http: HttpClient) { }

  /** GET all comments from post by post id */
  getAllCommentsByPostId(postId: string): Observable<Comment[]> {
    return this.http.get<Comment[]>(COMMENT_POST_API + '/' + postId + '/comments/all');
  }

  /** GET all comments from post by post id via page */
  getAllCommentsByPostIdPage(postId: string): Observable<Comment[]> {
    return this.http.get<Comment[]>(COMMENT_POST_API + '/' + postId + '/comments');
  }

  /** GET all posts by User name */
  getAllPostsByUsername(postId: string, username: string): Observable<Post[]> {
    return this.http.get<Post[]>(COMMENT_POST_API + '/' + postId + username);
  }

  /** GET all posts by User name */
  getAllPostsByUserId(postId: string, userId: string): Observable<Post[]> {
    return this.http.get<Post[]>(COMMENT_POST_API + '/' + postId + userId);
  }

  /** GET post by id */
  getPostById(postId: string, id: string): Observable<any> {
    return this.http.get<Playlist>(COMMENT_POST_API + '/' + postId + id);
  }

  /** GET all comments from post */
  getAllCommentsFromPost(postId: string, playlistId: string): Observable<Track[]> {
    return this.http.get<Track[]>(COMMENT_POST_API + '/' + postId + '/tracks');
  }

  /** POST add new post by id */
  addPlaylist(postId: string, playlist: Playlist): Observable<Playlist> {
    return this.http.post<Playlist>(COMMENT_POST_API + '/' + postId, playlist);
  }

  /** DELETE post by id */
  deletePlaylist(postId: string, id: number): Observable<any> {
    return this.http.delete<Playlist>(COMMENT_POST_API + '/' + postId + id);
  }
}
