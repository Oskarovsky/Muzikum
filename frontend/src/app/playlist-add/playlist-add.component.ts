import { Component, OnInit } from '@angular/core';
import {PlaylistService} from "../shared/playlist/playlist.service";
import {Playlist} from "../playlist/model/playlist";
import {Track} from "../track/model/track";
import {TrackService} from "../shared/track/track.service";

@Component({
  selector: 'app-playlist-add',
  templateUrl: './playlist-add.component.html',
  styleUrls: ['./playlist-add.component.css']
})
export class PlaylistAddComponent implements OnInit {

  playlists: Playlist[] = [];

  tracks: Track[] = [];

  modelPlaylist: Playlist = {
    id: null,
    name: '',
  };

  modelTrack: Track = {
    title: '',
    artist: '',
    points: null,
    genre: '',
    version: '',
    url: '',
    position: null
  };

  constructor(private playlistService: PlaylistService,
              private trackService: TrackService) { }

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
      id: null,
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