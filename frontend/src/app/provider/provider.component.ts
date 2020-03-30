import {Component, Input, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {ProviderService} from "../shared/provider/provider.service";
import {TrackService} from "../shared/track/track.service";
import {Provider} from "../model/provider";
import {ProviderListComponent} from "../provider-list/provider-list.component";
import {Location} from '@angular/common';

@Component({
  selector: 'app-provider',
  templateUrl: './provider.component.html',
  styleUrls: ['./provider.component.css']
})
export class ProviderComponent implements OnInit {

  provider: Provider;

  constructor(private providerService: ProviderService,
              private trackService: TrackService,
              private route: ActivatedRoute,
              private location: Location,
              private router: Router) { }


  ngOnInit() {
    this.getProvider();
  }

  getProvider(): void {
    const id = +this.route.snapshot.paramMap.get('id');
    this.providerService.getProvider(String(id))
      .subscribe(provider => this.provider = provider)
  }

  goBack(): void {
    this.location.back();
  }

}
