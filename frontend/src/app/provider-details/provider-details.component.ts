import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from "@angular/router";
import {ProviderService} from "../shared/provider/provider.service";

@Component({
  selector: 'app-provider-details',
  templateUrl: './provider-details.component.html',
  styleUrls: ['./provider-details.component.css']
})
export class ProviderDetailsComponent implements OnInit {

  provider: Object;

  constructor(public route: ActivatedRoute,
              public router: Router,
              private providerService: ProviderService) { }

  ngOnInit() {

  }

}
