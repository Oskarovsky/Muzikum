import {Playlist} from '../../../playlists/playlist/model/playlist';
import {Video} from '../../../videos/video/model/video';
import { User } from 'src/app/services/user/user';

export interface Track {
  id: number;
  title: string;
  artist: string;
  points: number;
  genre: string;
  version: string;
  url: string;
  position: number;
  createdAt: string;
  playlist: Playlist;
  video: Video;
  favoriteUsers: User[];
  user: User;
}
