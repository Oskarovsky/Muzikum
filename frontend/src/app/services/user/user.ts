import {Track} from '../../tracks/track/model/track';

export class User {
  id: number;
  username: string;
  email: string;
  password: string;
  createdAt: string;
  favoriteTracks: Track[];
  provider: string;
  providerId: number;
}
