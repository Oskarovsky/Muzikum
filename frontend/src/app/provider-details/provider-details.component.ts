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

  constructor(private providerService: ProviderService,
              private trackService: TrackService,
              private route: ActivatedRoute,
              private location: Location,
              private router: Router) { }


  ngOnInit() {
    this.sub = this.route.params.subscribe(params => {
      const id = params.id;
      if (id) {
        this.providerService.getProvider(id).subscribe((data: any) => {
          this.provider = data;
        });
        this.providerService.getAllGenresFromProvider(id).subscribe((genre: any) => {
          this.genres = genre;
        });
      }
    })
  }


}
