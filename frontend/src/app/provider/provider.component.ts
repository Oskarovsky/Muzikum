import {Component, OnInit} from '@angular/core';
import {Subscription} from "rxjs";
import {ActivatedRoute, Router} from "@angular/router";
import {ProviderService} from "../shared/provider/provider.service";
import {TrackService} from "../shared/track/track.service";

@Component({
  selector: 'app-provider',
  templateUrl: './provider.component.html',
  styleUrls: ['./provider.component.css']
})
export class ProviderComponent implements OnInit {

  provider: any = {};

  sub: Subscription;

  constructor(private providerService: ProviderService,
              private trackService: TrackService,
              private route: ActivatedRoute,
              private router: Router) { }

  ngOnInit() {
    this.sub = this.route.params.subscribe(params => {
      const id = params.id;
      if (id) {
        this.providerService.get(id).subscribe((provider: any) => {
          this.provider = provider;
        });
      }
    });
  }

}
