import { Injectable } from '@angular/core';
import {HttpClient, HttpEvent, HttpHeaders, HttpParams, HttpRequest} from '@angular/common/http';
import { Observable } from 'rxjs';
import { Track } from 'src/app/tracks/track/model/track';
import {environment} from '../../../environments/environment';
import {TrackResponse} from '../../tracks/track/model/track-response';
import {TrackComment} from '../../tracks/track/model/track-comment';
import {TrackKrakenfiles} from '../../tracks/track/model/track-krakenfiles';

const API: string = environment.serverUrl;
const TRACK_API = API + '/tracks';
const PROVIDER_API = API + '/providers';

const httpOptions = {
  headers: new HttpHeaders({
    // 'Content-Type': 'application/json',
    'Access-Control-Allow-Origin' : '*',
    'x-Trigger': 'CORS' })
};

@Injectable({providedIn: 'root'})
export class TrackService {

  constructor(private http: HttpClient) {
  }

  getAllTracks(): Observable<Track[]> {
    return this.http.get<Track[]>(TRACK_API + '/findAll');
  }

  getTrackById(id: number) {
    return this.http.get(TRACK_API + '/id/' + id, httpOptions);
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

  getTrackByUrl(urlX: string): Observable<any> {
    const data = {url: urlX};
    return this.http.get(TRACK_API + '/getByUrl', {params: data});
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
    return this.http.get<TrackResponse>(TRACK_API + '/genre/' + genre + '/pages/' + page, httpOptions);
  }

  getTrackPageByUserUsername(username: string, page: number): Observable<TrackResponse> {
    return this.http.get<TrackResponse>(TRACK_API + '/user/' + username + '/pages/' + page);
  }

  getAllTrackCommentsByTrackId(trackId: number): Observable<TrackComment[]> {
    return this.http.get<TrackComment[]>(TRACK_API + '/id/' + trackId + '/comments');
  }

  addTrackComment(trackComment: TrackComment): Observable<TrackComment> {
    return this.http.post<TrackComment>(TRACK_API + '/comment/add', trackComment);
  }

  deleteTrackCommentById(commentId: number) {
    return this.http.delete(TRACK_API + '/comment/' + commentId + '/remove');
  }

  // getNumberOfTracksAddedByTheUser(username: string) {
  //   return this.http.get<any>(TRACK_API + '/user/' + username + '/count');
  // }

}
