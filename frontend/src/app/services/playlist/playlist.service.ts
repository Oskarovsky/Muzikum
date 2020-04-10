import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Playlist } from '../../playlist/model/playlist';
import { Track } from 'src/app/track/model/track';

@Injectable({providedIn: 'root'})
export class PlaylistService {

  // public API = '//localhost:8080/api';
  public API = '//91.205.75.145:8080/api';
  public PLAYLIST_API = this.API + '/playlist';

  tracks: Track[] = [];

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
  getAllTracksFromPlaylist(playlistId: string): Observable<Track[]> {
    return this.http.get<Track[]>(this.PLAYLIST_API + '/' + playlistId + '/tracks');
  }

  addPlaylist(playlist: Playlist): Observable<Playlist> {
    return this.http.post<Playlist>(this.PLAYLIST_API  + '/add', playlist);
  }

  deletePlaylist(id: number): Observable<any> {
    return this.http.delete<Playlist>(this.PLAYLIST_API + '/' + id);
  }
}
