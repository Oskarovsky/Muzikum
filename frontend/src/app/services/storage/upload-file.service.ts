import { Injectable } from '@angular/core';
import {HttpClient, HttpEvent, HttpParams, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from '../../../environments/environment';

const API: string = environment.serverUrl;
const STORAGE_API = API + '/storage';

@Injectable({
  providedIn: 'root'
})
export class UploadFileService {

  constructor(private http: HttpClient) {}

  upload(file: File, username: string, destination: string): Observable<HttpEvent<any>> {
    const formData: FormData = new FormData();
    formData.append('file', file);
    formData.append('username', username);
    formData.append('destination', destination);
    const req = new HttpRequest('POST', STORAGE_API + '/upload', formData, {
      reportProgress: true,
      responseType: 'json'
    });
    return this.http.request(req);
  }

  uploadCover(file: File, username: string, trackUrl: string): Observable<HttpEvent<any>> {
    const formData: FormData = new FormData();
    formData.append('file', file);
    formData.append('username', username);
    formData.append('trackUrl', trackUrl);
    const req = new HttpRequest('POST', STORAGE_API + '/uploadFileCoverTrack', formData, {
      reportProgress: true,
      responseType: 'json'
    });
    // console.log('ZXCV = ' + 'trackCover_'.concat(file.name));
    // console.log('ZXC = ' + JSON.stringify(this.getFileIdByFilename('trackCover_'.concat(file.name))));

    // this.getFileIdByFilename('trackCover_'.concat(file.name)).subscribe(
    //   response => {
    //     console.log('ZXCVBNM = ' + response);
    //   });
    return this.http.request(req);
  }

  getTrackCoverByTrackId(trackId: number): Observable<any> {
    return this.http.get(STORAGE_API + '/getTrackCover/' + trackId);
  }

  getFile(username: string): Observable<Blob> {
    return this.http.get(STORAGE_API + '/' + username + '/avatar', { responseType: 'blob' });
  }

  getCoverFile(trackId: number): Observable<Blob> {
    return this.http.get(STORAGE_API + '/' + trackId + '/cover', { responseType: 'blob' });
  }

}
