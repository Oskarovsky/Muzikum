import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';

import {TrackService} from "../services/track/track.service";
import {HttpClient} from "@angular/common/http";
import {Track} from "./model/track";

@Component({
  selector: 'app-track',
  templateUrl: './track.component.html',
  styleUrls: ['./track.component.css']
})
export class TrackComponent implements OnInit {

  public API = '//localhost:8080';
  public TRACK_API = this.API + '/tracks';
  public PROVIDER_API = this.API + '/providers';

  track: any = {};
  tracks: Track[] = [];

  sub: Subscription;


  constructor(private trackService: TrackService,
              private route: ActivatedRoute,
              private router: Router,
              private http: HttpClient) { }

  ngOnInit() {
    this.sub = this.route.params.subscribe(params => {
      const id = params.id;
      if (id) {
        this.trackService.getTrackById(id).subscribe((track: any) => {
          this.track = track;
        })
      }
    });
  }

  public getAllTracks(){
    this.trackService.getAllTracks().subscribe(
      result => {
        this.tracks = result;
      },
      error => {
        alert('An error has occurred while downloading tracks')
      }
    )
  }

  public getTrackById(id: string) {
    this.trackService.getTrackById(id).subscribe(
    )
  }

}
