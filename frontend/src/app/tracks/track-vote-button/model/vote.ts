import { User } from 'src/app/services/user/user';
import { Track } from '../../track/model/track';


export class Vote {
  id: number;
  user: User;
  track: Track;
}
