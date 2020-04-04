import { Component, OnInit } from '@angular/core';
import {Playlist} from "./model/playlist";

@Component({
  selector: 'app-playlist',
  templateUrl: './playlist.component.html',
  styleUrls: ['./playlist.component.css']
})
export class PlaylistComponent implements OnInit {

  playlists: Playlist[] = [];

  constructor(private ) { }

  ngOnInit() {
    this.ge
  }

}
