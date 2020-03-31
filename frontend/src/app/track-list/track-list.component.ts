import { Component, OnInit } from '@angular/core';
import { TrackService} from "../shared/track/track.service";
import {Subscription} from "rxjs";
import {ProviderService} from "../shared/provider/provider.service";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-track-list',
  templateUrl: './track-list.component.html',
  styleUrls: ['./track-list.component.css']
})
export class TrackListComponent implements OnInit {

  tracks: Array<any>;

  sub: Subscription;

  constructor(private trackService: TrackService,
              private providerService: ProviderService,
              private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.sub = this.route.params.subscribe(params => {
      const id = params.id;
      const genre = params.genre;
      const providerName = params.providerName;
      if (id && !genre) {
        this.trackService.getFromProvider(id).subscribe((track: any) => {
          this.tracks = track;
        });
      } else if (id && genre) {
        this.trackService.getFromProviderByGenre(id, genre).subscribe((track: any) => {
          this.tracks = track;
        })
      } else if (!id && genre) {
        this.trackService.getByGenre(genre).subscribe((track: any) => {
          this.tracks = track;
        })
      } else if (providerName && !id && !genre) {
        this.trackService.getByProviderName(providerName).subscribe((track: any) => {
          this.tracks = track;
        })
      }
    })
  }
}
