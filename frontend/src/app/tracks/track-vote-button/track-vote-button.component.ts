import {AfterViewInit, Component, Input, OnChanges, OnInit} from '@angular/core';
import {Track} from '../track/model/track';
import {TokenStorageService} from '../../services/auth/token-storage.service';
import {TrackService} from '../../services/track/track.service';
import {VotePayload} from './model/vote-payload';
import {throwError} from 'rxjs';
import {VoteService} from '../../services/vote/vote.service';
import { VoteType } from './model/vote-type';

@Component({
  selector: 'app-track-vote-button',
  templateUrl: './track-vote-button.component.html',
  styleUrls: ['./track-vote-button.component.css']
})
export class TrackVoteButtonComponent implements OnInit, AfterViewInit {

  @Input() track: Track;
  votePayload: VotePayload;
  isLoggedIn = false;
  isVoted = false;

  constructor(private tokenStorage: TokenStorageService,
              private trackService: TrackService,
              private voteService: VoteService) {
    this.votePayload = {
      voteType: undefined,
      trackId: undefined,
      userId: undefined
    };
  }

  ngOnInit() {
    if (this.tokenStorage.getToken()) {
      this.isLoggedIn = true;
      this.votePayload.userId = this.tokenStorage.getUser().id;
      this.checkIfUserVotedForTrack(this.track.id, this.tokenStorage.getUser().id);
    }
    this.updateVoteDetails();
  }

  ngAfterViewInit() {
    // this.checkIfUserVotedForTrack(this.track.id, this.tokenStorage.getUser().id);
  }

  upvotePost() {
    this.votePayload.voteType = VoteType.UPVOTE;
    this.vote();
  }

  downvotePost() {
    this.votePayload.voteType = VoteType.DOWNVOTE;
    this.vote();
  }

  checkIfUserVotedForTrack(trackId: number, userId: number) {
    this.voteService.checkIfUserVotedForTrack(trackId, userId).subscribe(
      response => {
        this.isVoted = response;
      }
    );
  }

  private vote() {
    this.votePayload.trackId = this.track.id;
    this.voteService.voteForTrack(this.votePayload).subscribe(() => {
      this.updateVoteDetails();
    }, error => {
      throwError(error);
    });
  }

  private updateVoteDetails() {
    this.trackService.getTrackById(this.track.id).subscribe((track: any) => {
      this.track = track;
    });
  }

}
