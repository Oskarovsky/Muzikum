import {Track} from './track';

export class TrackResponse {
  trackList: Track[];
  totalPages: number;
  totalElements: number;
  numberPage: number;
}
