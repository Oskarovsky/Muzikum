import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import { Observable } from 'rxjs';
import { Track } from 'src/app/track/model/track';

@Injectable({providedIn: 'root'})
export class TrackService {

  public API = '//localhost:8080/api';
  // public API = '//91.205.75.145:8080/api';
  public TRACK_API = this.API + '/tracks';
  public PROVIDER_API = this.API + '/providers';
  public PLAYLIST_API = this.API + '/playlist';



  constructor(private http: HttpClient) {
  }

  getAllTracks(): Observable<Track[]> {
    return this.http.get<Track[]>(this.TRACK_API + '/findAll');
  }

  getTrackById(id: string) {
    return this.http.get(this.TRACK_API + '/' + id);
  }

  getTracksFromProviderByGenre(id: string, genre: string) {
    return this.http.get(this.PROVIDER_API + '/' + id + '/' + genre);
  }

  getTracksByProviderId(id: string) {
    return this.http.get(this.PROVIDER_API + '/' + id + '/tracks');
  }

  getTracksByGenre(genre: string) {
    return this.http.get(this.TRACK_API + '/genre/' + genre)
  }

  getTracksByProviderName(providerName: string) {
    return this.http.get(this.PROVIDER_API + '/' + providerName + '/all-tracks')
  }

  addTrackToRanking(track: Track): Observable<any> {
    return this.http.post<Track>(this.TRACK_API + "/addToRanking", track)
  }

  saveTrackToPlaylist(track: Track): Observable<Track> {
    return this.http.post<Track>(this.TRACK_API + '/add', track);
  }

  deleteTrackFromPlaylist(id: number): Observable<any> {
    return this.http.delete(this.TRACK_API + '/' + id);
  }

}
