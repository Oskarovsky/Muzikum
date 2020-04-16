import { Component, OnInit } from '@angular/core';
import {Playlist} from './model/playlist';
import {PlaylistService} from '../services/playlist/playlist.service';
import {Subscription} from 'rxjs';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-playlist',
  templateUrl: './playlist.component.html',
  styleUrls: ['./playlist.component.css']
})
export class PlaylistComponent implements OnInit {

  playlists: Playlist[] = [];
  searchText: string;
  sub: Subscription;

  constructor(private playlistService: PlaylistService,
              private route: ActivatedRoute) { }

  ngOnInit() {
    this.getAllPlaylistByUsername();
  }

  public getAllPlaylistByUsername() {
    this.sub = this.route.params.subscribe(params => {
      const username = params.username;
      if (username) {
        this.playlistService.getAllPlaylistsByUsername(username).subscribe(
          response => {
            this.playlists = response;
          },
          error => {
            alert('An error with fetching playlist has occurred');
          }
        );
      }
    });
  }

  deletePlaylist(id: number) {
    if (confirm('Czy na pewno chcesz usunąć playlistę?')) {
      this.playlistService.deletePlaylist(id).subscribe(
        response => {
          this.playlists.splice(id, 1);
          window.location.reload();
        },
        error => {
          alert('Could not delete playlist');
        }
      );
    }
  }
}
