import {Component, Input, OnInit} from '@angular/core';
import { ActivatedRoute, Router } from "@angular/router";
import {ProviderService} from "../shared/provider/provider.service";
import {Provider} from "../model/provider";
import {TrackService} from "../shared/track/track.service";
import {ProviderListComponent} from "../provider-list/provider-list.component";
import {Location} from "@angular/common";

@Component({
  selector: 'app-provider-details',
  templateUrl: './provider-details.component.html',
  styleUrls: ['./provider-details.component.css']
})
export class ProviderDetailsComponent implements OnInit {

  @Input() provider: Provider;

  constructor(private providerService: ProviderService,
              private trackService: TrackService,
              private route: ActivatedRoute,
              private location: Location,
              private router: Router) { }

  ngOnInit() {

  }

}
