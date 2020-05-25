import {User} from '../../../services/user/user';

export class Playlist {
  name: string;
  id: number;
  user: User;
  points: number;
  views: number;
}
