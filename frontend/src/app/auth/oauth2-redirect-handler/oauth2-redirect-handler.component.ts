import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-oauth2-redirect-handler',
  templateUrl: './oauth2-redirect-handler.component.html',
  styleUrls: ['./oauth2-redirect-handler.component.scss']
})
export class Oauth2RedirectHandlerComponent implements OnInit {

  result: string;

  constructor(private activatedRoute: ActivatedRoute) {
    this.activatedRoute.queryParams.subscribe(params => {
      const errorParam = params.error;
      const tokenParam = params.token;
      if (tokenParam) {
        this.result = 'SUCCESS';
      } else {
        this.result = 'ERROR';
      }
    });
  }

  ngOnInit() {
  }

}
