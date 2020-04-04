import { Component, OnInit } from '@angular/core';
import {Playlist} from "./model/playlist";
import {PlaylistService} from "../shared/playlist/playlist.service";

@Component({
  selector: 'app-playlist',
  templateUrl: './playlist.component.html',
  styleUrls: ['./playlist.component.css']
})
export class PlaylistComponent implements OnInit {

  playlists: Playlist[] = [];

  constructor(private playlistService: PlaylistService) { }

  ngOnInit() {
    this.getAllPlaylists();
  }

  public getAllPlaylists() {
    this.playlistService.getAllPlaylists().subscribe(
      res => {
        this.playlists = res;
      },
      error => {
        alert("An error with fetching playlists has occurred")
      }
    )
  }

  deletePlaylist(playlist: Playlist) {

  }
}
