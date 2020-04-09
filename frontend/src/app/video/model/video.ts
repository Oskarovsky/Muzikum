import { Track } from 'src/app/track/model/track';

export class Video {
  id: number;
  name: string;
  url: string;
  tracks: Track[];
}
