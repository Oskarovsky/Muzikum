import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import { Track } from "../model/track";
import { FormGroup, FormControl } from '@angular/forms';

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

  addTrackToRanking(): void {
    console.log('CLICK !!!');
    alert(this.model.title)
  }

  constructor(private route: ActivatedRoute) { }

  ngOnInit() {
  }

  trackForm = new FormGroup({
    title: new FormControl(''),
    artist: new FormControl(''),
    version: new FormControl(''),
    url: new FormControl(''),
    points: new FormControl('')
  })
}
