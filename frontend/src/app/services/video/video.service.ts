import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Track } from '../../track/model/track';
import {Observable} from 'rxjs';
import {Playlist} from '../../playlist/model/playlist';
import {Video} from '../../video/model/video';
import {DomSanitizer} from '@angular/platform-browser';

@Injectable({providedIn: 'root'})
export class VideoService {

  public API = '//localhost:8080/api';
  // public API = '//91.205.75.145:8080/api';
  public VIDEO_API = this.API + '/video';

  tracks: Track[] = [];

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private http: HttpClient,
              private sanitizer: DomSanitizer) { }

  /** GET all video */
  getAllVideos(): Observable<Video[]> {
    return this.http.get<Video[]>(this.VIDEO_API + '/findAll');
  }

  /** GET video by id */
  getVideo(id: string): Observable<any> {
    return this.http.get<Video>(this.VIDEO_API + '/' + id);
  }

  addVideo(video: Video): Observable<Video> {
    return this.http.post<Video>(this.VIDEO_API  + '/add', video);
  }

  deleteVideo(id: number): Observable<any> {
    return this.http.delete<Video>(this.VIDEO_API + '/' + id);
  }

}
