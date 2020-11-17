import {Injectable} from '@angular/core';
import {environment} from '../../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

const API: string = environment.serverUrl;

@Injectable({
  providedIn: 'root'
})
export class TokenConfirmationService {

  constructor(private http: HttpClient) { }

  /** GET Token confirmation */
  tokenConfirm(token: string): Observable<any> {
    return this.http.get(API + '/auth/confirmAccount/' + token);
  }

}
