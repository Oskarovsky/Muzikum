import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-zippy-player',
  templateUrl: './zippy-player.component.html',
  styleUrls: ['./zippy-player.component.scss']
})
export class ZippyPlayerComponent implements OnInit {

  constructor(private router: Router,
              private route: ActivatedRoute) {}

  key: string;
  server: string;
  autoplay: string;

  ngOnInit() {
    this.route.queryParams.subscribe(params => {
      this.key = params.key;
      this.server = params.server;
      this.autoplay = params.autoplay;
    });
  }

}
