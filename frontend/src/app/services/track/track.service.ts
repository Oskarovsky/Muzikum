import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import { Observable } from 'rxjs';
import { Track } from 'src/app/tracks/track/model/track';
import {environment} from '../../../environments/environment';
import {TrackResponse} from '../../tracks/track/model/track-response';

const API: string = environment.serverUrl;
const TRACK_API = API + '/tracks';
const PROVIDER_API = API + '/providers';

@Injectable({providedIn: 'root'})
export class TrackService {

  constructor(private http: HttpClient) {
  }

  getAllTracks(): Observable<Track[]> {
    return this.http.get<Track[]>(TRACK_API + '/findAll');
  }

  getTrackById(id: string) {
    return this.http.get(TRACK_API + '/' + id);
  }

  getTracksFromProviderByGenre(id: string, genre: string) {
    return this.http.get(PROVIDER_API + '/' + id + '/' + genre);
  }

  getTracksByProviderId(id: string) {
    return this.http.get(PROVIDER_API + '/' + id + '/tracks');
  }

  getTracksByGenre(genre: string) {
    return this.http.get(TRACK_API + '/genre/' + genre);
  }

  getTracksByProviderName(providerName: string) {
    return this.http.get(PROVIDER_API + '/' + providerName + '/all-tracks');
  }

  addTrackToRanking(track: Track): Observable<any> {
    return this.http.post<Track>(TRACK_API + '/addToRanking', track);
  }

  saveTrackToPlaylist(track: Track): Observable<Track> {
    return this.http.post<Track>(TRACK_API + '/add', track);
  }

  saveTrackToVideo(track: Track): Observable<Track> {
    return this.http.post<Track>(TRACK_API + '/add', track);
  }

  addTrack(track: Track): Observable<Track> {
    return this.http.post<Track>(TRACK_API + '/add', track);
  }

  deleteTrackFromPlaylist(id: number): Observable<any> {
    return this.http.delete(TRACK_API + '/' + id);
  }

  getRandomTrack(): Observable<Track> {
    return this.http.get<Track>(TRACK_API + '/random');
  }

  addTrackToFavorites(id: number, username: string): Observable<any> {
    return this.http.get<any>(TRACK_API + '/' + id + '/addToFavorites/' + username);
  }

  getMostPopularTrackByGenre(genre: string): Observable<Track> {
    return this.http.get<Track>(TRACK_API + '/genre/' + genre + '/top');
  }

  getLastAddedTracksByGenre(genre: string, numberOfTracks: number) {
    return this.http.get(TRACK_API + '/genre/' + genre + '/' + numberOfTracks);
  }

  getTopListByGenre(genre: string, numberOfTracks: number) {
    return this.http.get(TRACK_API + '/genre/' + genre + '/top/' + numberOfTracks);
  }

  getLastAddedTracksByUsername(username: string, numberOfTracks: number): Observable<Track[]> {
    return this.http.get<Track[]>(TRACK_API + '/user/' + username + '/' + numberOfTracks);
  }

  getLastAddedTracksByGenreOnlyWithUser(genre: string, numberOfTracks: number) {
    return this.http.get<Track>(TRACK_API + '/genre/' + genre + '/lastAddedByUser/' + numberOfTracks);
  }

  getPageTracksByGenre(params, genre: string): Observable<any> {
    return this.http.get(TRACK_API + '/genre/' + genre + '/list', params);
  }

  getTracksByGenreFromOnePage(genre: string, page: number): Observable<Track[]> {
    return this.http.get<Track[]>(TRACK_API + '/genre/' + genre + '/page/' + page);
  }

  getTrackPageByGenre(genre: string, page: number): Observable<TrackResponse> {
    return this.http.get<TrackResponse>(TRACK_API + '/genre/' + genre + '/pages/' + page);
  }

}
