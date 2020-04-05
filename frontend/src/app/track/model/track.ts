import {Playlist} from "../../playlist/model/playlist";

export interface Track {
  id: number;
  title: string;
  artist: string;
  points: number;
  genre: string;
  version: string;
  url: string;
  position: number;
  playlist: Playlist;
}
