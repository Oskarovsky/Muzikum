import { Component, OnInit } from '@angular/core';
import {Playlist} from "../playlist/model/playlist";
import {PlaylistService} from "../shared/playlist/playlist.service";
import {Subscription} from "rxjs";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-playlist-details',
  templateUrl: './playlist-details.component.html',
  styleUrls: ['./playlist-details.component.css']
})
export class PlaylistDetailsComponent implements OnInit {

  playlist: Playlist;

  sub: Subscription;

  constructor(private playlistService: PlaylistService,
              private route: ActivatedRoute) { }

  ngOnInit() {
    this.getPlaylistById();
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

}
