import {Component, Input, OnInit} from '@angular/core';
import {Subscription} from "rxjs";
import {ActivatedRoute, Router} from "@angular/router";
import {ProviderService} from "../shared/provider/provider.service";
import {TrackService} from "../shared/track/track.service";
import {Provider} from "../model/provider";
import {ProviderListComponent} from "../provider-list/provider-list.component";

@Component({
  selector: 'app-provider',
  templateUrl: './provider.component.html',
  styleUrls: ['./provider.component.css']
})
export class ProviderComponent implements OnInit {

  providers: Provider[];

  sub: Subscription;

  constructor(private providerService: ProviderService,
              private trackService: TrackService,
              private route: ActivatedRoute,
              private providerList: ProviderListComponent,
              private router: Router) { }


  ngOnInit() {
    this.sub = this.route.params.subscribe(params => {
      const id = params.id;
      if (id) {
        this.providerService.getProvider(id).subscribe((provider: any) => {
          this.providers = provider;
        });
      }
    });
  }

  getProvider(): void {
    this.providerService.getAll()
      .subscribe(providers => this.providers = providers)
  }

}
