import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {ProviderService} from "../shared/provider/provider.service";
import {TrackService} from "../shared/track/track.service";
import {Location} from "@angular/common";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-provider-details',
  templateUrl: './provider-details.component.html',
  styleUrls: ['./provider-details.component.css']
})
export class ProviderDetailsComponent implements OnInit {

  provider: any;

  genres: Array<any>;

  sub: Subscription;

  tracks: Array<any>;


  constructor(private providerService: ProviderService,
              private trackService: TrackService,
              private route: ActivatedRoute,
              private location: Location,
              private router: Router) { }


  ngOnInit() {
    this.sub = this.route.params.subscribe(params => {
      const id = params.id;
      if (id) {
        this.providerService.getProvider(id).subscribe(data => {
          this.provider = data;
        });
        this.providerService.getAllGenresFromProvider(id).subscribe((genre: any) => {
          this.genres = genre;
        });
        this.trackService.getTracksByProviderId(id).subscribe((track: any) => {
          this.tracks = track;
        })
      }
    })
  }

  getAllTracksFromProviderByGenre(id: string, genre: string) {
    this.trackService.getTracksFromProviderByGenre(id, genre).subscribe((data: any) => {
      this.tracks = data;
    })
  }


}
