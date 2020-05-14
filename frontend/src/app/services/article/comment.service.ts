import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Playlist } from '../../playlists/playlist/model/playlist';
import { Track } from 'src/app/tracks/track/model/track';
import {environment} from '../../../environments/environment';
import {Post} from '../../article/add-post/model/post';

const API: string = environment.serverUrl;
const POST_API = API + '/posts';

@Injectable({providedIn: 'root'})
export class CommentService {

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

  /** GET all comments from post */
  getAllCommentsFromPost(playlistId: string): Observable<Track[]> {
    return this.http.get<Track[]>(POST_API + '/' + playlistId + '/tracks');
  }

  /** POST add new post by id */
  addPlaylist(playlist: Playlist): Observable<Playlist> {
    return this.http.post<Playlist>(POST_API  + '/add', playlist);
  }

  /** DELETE post by id */
  deletePlaylist(id: number): Observable<any> {
    return this.http.delete<Playlist>(POST_API + '/' + id);
  }
}
