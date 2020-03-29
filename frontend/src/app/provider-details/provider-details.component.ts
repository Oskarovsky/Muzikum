import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from "@angular/router";
import {ProviderService} from "../shared/provider/provider.service";

@Component({
  selector: 'app-provider-details',
  templateUrl: './provider-details.component.html',
  styleUrls: ['./provider-details.component.css']
})
export class ProviderDetailsComponent implements OnInit {

  providers: Array<any>;

  details = { id: null, name: '', description: '', url: ''};

  constructor(public route: ActivatedRoute,
              public router: Router,
              private providerService: ProviderService) { }

  ngOnInit() {
    this.providerService.getAll().subscribe(data => {
      this.providers = data;
    });

    if (this.route.snapshot.paramMap.get('id') != null) {
      const id = parseInt(this.route.snapshot.paramMap.get('id'), 0);
      this.details = this.providers.find(x => x.id === id);
    }
  }

}
