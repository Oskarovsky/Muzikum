import {Track} from './track';
import {User} from '../../../services/user/user';

export class TrackComment {
  id: number;
  text: string;
  track: Track;
  user: User;
  createdAt: string;
}
