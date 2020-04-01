import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import { Track } from "../track/model/track";
import { FormGroup, FormControl } from '@angular/forms';
import {HttpClient} from "@angular/common/http";
import {TrackService} from "../shared/track/track.service";

@Component({
  selector: 'app-toplist-edit',
  templateUrl: './toplist-edit.component.html',
  styleUrls: ['./toplist-edit.component.css']
})
export class ToplistEditComponent implements OnInit {

  model: Track = {
    //id: null,
    title: '',
    artist: '',
    points: null,
    genre: '',
    version: '',
    url: '',
    position: null
  };


  constructor(private http: HttpClient,
              private trackService: TrackService,
              private route: ActivatedRoute) {

  }

  ngOnInit() {
  }

  addTrackToRanking(): void {
    this.trackService.addTrackToRanking(this.model)
  }

}