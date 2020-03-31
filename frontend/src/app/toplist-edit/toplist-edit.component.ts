import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-toplist-edit',
  templateUrl: './toplist-edit.component.html',
  styleUrls: ['./toplist-edit.component.css']
})
export class ToplistEditComponent implements OnInit {

  constructor(private route: ActivatedRoute) { }

  ngOnInit() {
  }


}
