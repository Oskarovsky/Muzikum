import { Component, OnInit } from '@angular/core';
import {Playlist} from "./model/playlist";
import {PlaylistService} from "../services/playlist/playlist.service";

@Component({
  selector: 'app-playlist',
  templateUrl: './playlist.component.html',
  styleUrls: ['./playlist.component.css']
})
export class PlaylistComponent implements OnInit {

  playlists: Playlist[] = [];
  searchText: string;

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

  deletePlaylist(id: number) {
    if(confirm('Czy na pewno chcesz usunąć playlistę?')) {
      this.playlistService.deletePlaylist(id).subscribe(
        response => {
          this.playlists.splice(id, 1);
          window.location.reload();
        },
        error => {
          alert("Could not delete playlist");
        }
      )
    }
  }
}
