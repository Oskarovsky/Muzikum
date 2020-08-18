import {VoteType} from './vote-type';

export class VotePayload {
  voteType: VoteType;
  trackId: number;
  userId: number;
}
