import {Playlist} from '../../../playlists/playlist/model/playlist';
import {Video} from '../../../videos/video/model/video';
import { User } from 'src/app/services/user/user';
import {SafeResourceUrl} from '@angular/platform-browser';
import {Cover} from './cover';

export interface Track {
  id: number;
  title: string;
  artist: string;
  points: number;
  genre: string;
  version: string;
  url: string;
  urlSource: string;
  urlPlugin: string;
  safeUrl: SafeResourceUrl;
  position: number;
  createdAt: string;
  playlist: Playlist;
  video: Video;
  favoriteUsers: User[];
  user: User;
  cover: Cover;
}
