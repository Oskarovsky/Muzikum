import { Injectable } from '@angular/core';
import {environment} from '../../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Vote} from '../../voting/vote';
import {Track} from '../../tracks/track/model/track';
import {VotePayload} from '../../tracks/track-vote-button/model/vote-payload';

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

  addVoteForTrackById(id: number, username: string): Observable<any> {
    return this.http.get(VOTE_API + '/track/' + id + '/add/' + username);
  }

  voteForTrack(votePayload: VotePayload): Observable<any> {
    return this.http.post(VOTE_API + '/track', votePayload);
  }

  getAllVotedTracksByUser(username: string): Observable<any> {
    return this.http.get(VOTE_API + '/' + username + '/tracks');
  }

  getAllVotedTracksIdsByUser(username: string): Observable<any> {
    return this.http.get(VOTE_API + '/' + username + '/tracks/ids');
  }

  checkIfUserVotedForTrack(trackId: number, userId: number): Observable<any> {
    return this.http.get(VOTE_API + '/track/' + trackId + '/checkVote/' + userId);
  }
}
