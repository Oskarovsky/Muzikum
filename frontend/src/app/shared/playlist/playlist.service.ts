import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {Playlist} from "../../playlist/model/playlist";

@Injectable({providedIn: 'root'})
export class PlaylistService {

  public API = '//localhost:8080';
  public PLAYLIST_API = this.API + '/playlist';

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private http: HttpClient) { }

  /** GET all playlists */
  getAllPlaylists(): Observable<Playlist[]> {
    return this.http.get<Playlist[]>(this.PLAYLIST_API + '/findAll');
  }

  /** GET playlist by id */
  getPlaylist(id: string): Observable<any> {
    return this.http.get<Playlist>(this.PLAYLIST_API + '/' + id);
  }

  /** GET all tracks from playlist */
  getAllTracksFromPlaylist(id: string): Observable<any> {
    return this.http.get<any[]>(this.PLAYLIST_API + '/' + id + '/tracks');
  }

  addPlaylist(playlist: Playlist): Observable<Playlist> {
    return this.http.post<Playlist>(this.PLAYLIST_API  + '/add', playlist);
  }

  deletePlaylist(id: string): Observable<any> {
    return this.http.delete<Playlist>(this.PLAYLIST_API + '/' + id);
  }
}
