import {Track} from '../../tracks/track/model/track';

export class User {
  id: number;
  email: string;
  username: string;
  password: string;
  createdAt: string;
  favoriteTracks: Track[];
  imageUrl: string;
  provider: string;
  providerId: number;
}
