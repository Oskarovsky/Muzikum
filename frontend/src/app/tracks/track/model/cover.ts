import {Track} from './track';

export interface Cover {
  id: number;
  name: string;
  type: string;
  url: string;
  track: Track;
}
