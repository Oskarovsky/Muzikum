import { Component, OnInit } from '@angular/core';
import {Playlist} from "../playlist/model/playlist";
import {Track} from "../track/model/track";
import {PlaylistService} from "../shared/playlist/playlist.service";
import {TrackService} from "../shared/track/track.service";
import {Subscription} from "rxjs";
import {ActivatedRoute, Route} from "@angular/router";

@Component({
  selector: 'app-playlist-edit',
  templateUrl: './playlist-edit.component.html',
  styleUrls: ['./playlist-edit.component.css']
})
export class PlaylistEditComponent implements OnInit {

  tracks: Track[] = [];

  sub: Subscription;

  playlist: Playlist;

  playlist_id;

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
              private trackService: TrackService,
              private route: ActivatedRoute) { }

  ngOnInit() {
    this.getPlaylistById();
    this.getAllTracksFromPlaylist();
  }
  getPlaylist() {
    this.sub = this.route.params.subscribe(params => {
      const id = params.id;
      this.playlist_id = id;
      if (id) {
        this.playlistService.getPlaylist(id).subscribe((playlist: any) => {
          this.playlist = playlist;
        })
      }
    })
  }

  public getAllTracksFromPlaylist() {
    this.sub = this.route.params.subscribe(params => {
      const id = params.id;
      if (id) {
        this.playlistService.getAllTracksFromPlaylist(id).subscribe(
          response => {
            this.tracks = response;
          },
          error => {
            alert("An error with fetching tracks has occurred")
          }
        )
      }
    })
  }

  updatePlaylist(updatedPlaylist: Playlist) {
    updatedPlaylist.id = this.playlist_id;
    this.playlistService.addPlaylist(updatedPlaylist).subscribe(
      result => {

      },
      error => {
        alert("An error with updating playlists has occurred")
      }
    )
  }

  public getPlaylistById() {
    this.sub = this.route.params.subscribe(params => {
      const id = params.id;
      if (id) {
        this.playlistService.getPlaylist(id).subscribe(
          response => {
            this.playlist = response;
          },
          error => {
            alert("An error with fetching playlist has occurred")
          })
      }
    })
  }

  deleteTrack(track: Track) {

  }
}
