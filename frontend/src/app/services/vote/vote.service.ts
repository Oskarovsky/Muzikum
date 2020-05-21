import { Injectable } from '@angular/core';
import {environment} from '../../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Vote} from '../../voting/vote';
import {Track} from '../../tracks/track/model/track';

const API: string = environment.serverUrl;
const VOTE_API = API + '/vote';

@Injectable({
  providedIn: 'root'
})
export class VoteService {

  constructor(private http: HttpClient) { }

  getVotesByTrackId(id: string): Observable<Vote[]> {
    return this.http.get<Vote[]>(VOTE_API + '/track/' + id);
  }

  getNumberOfVotesByTrackId(id: string) {
    return this.http.get(VOTE_API + '/track/' + id + '/all');
  }

  addVoteForTrackById(id: string, track: Track): Observable<any> {
    return this.http.post<Vote>(VOTE_API + '/track' + id + '/add', track);
  }
}
