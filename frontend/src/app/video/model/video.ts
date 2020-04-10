import { Track } from 'src/app/track/model/track';
import {SafeResourceUrl} from '@angular/platform-browser';

export class Video {
  id: number;
  name: string;
  url: string;
  safeUrl: SafeResourceUrl;
  tracks: Track[];
}
