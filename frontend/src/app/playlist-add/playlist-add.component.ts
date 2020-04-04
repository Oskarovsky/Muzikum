import { Component, OnInit } from '@angular/core';
import {PlaylistService} from "../shared/playlist/playlist.service";
import {Playlist} from "../playlist/model/playlist";

@Component({
  selector: 'app-playlist-add',
  templateUrl: './playlist-add.component.html',
  styleUrls: ['./playlist-add.component.css']
})
export class PlaylistAddComponent implements OnInit {

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
  createPlaylist() {
    let newPlaylist: Playlist = {
      name: 'New Playlist'
    };
    this.playlistService.addPlaylist(newPlaylist).subscribe(
      result => {
        newPlaylist.name = result.name;
        this.playlists.push(newPlaylist);
      },
      error => {
        alert('An error has occurred while saving the playlist')
      }
    )

  }

}
