import { Injectable } from '@angular/core';
import { HttpClient, HttpEvent, HttpRequest } from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UploadFileService {

  // public API = 'http://91.205.75.145:8080/api';
  public API = '//localhost:8080/api';
  public STORAGE_API = this.API + '/storage';

  constructor(private http: HttpClient) {
  }

  upload(file: File, username: string): Observable<HttpEvent<any>> {
    const formData: FormData = new FormData();
    formData.append('file', file);
    formData.append('username', username);
    const req = new HttpRequest('POST', this.STORAGE_API + '/upload', formData, {
      reportProgress: true,
      responseType: 'json'
    });
    return this.http.request(req);
  }

  getFile(username: string): Observable<Blob> {
    return this.http.get(this.STORAGE_API + '/' + username + '/avatar', { responseType: 'blob' });
  }
}
