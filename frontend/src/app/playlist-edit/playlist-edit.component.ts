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

  modelPlaylist: Playlist = {
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

  }

  getPlaylist(id: string) {
    this.sub = this.route.params.subscribe(params => {
      const id = params.id;
      if (id) {
        this.playlistService.getPlaylist(id).subscribe((playlist: any) => {
          this.playlist = playlist;
        })
      }
    })
  }

  updatePlaylist(updatedPlaylist: Playlist) {
    this.playlistService.addPlaylist(updatedPlaylist).subscribe(
      result => {

      },
      error => {
        alert("An error with updating playlists has occurred")
      }
    )
  }
}
