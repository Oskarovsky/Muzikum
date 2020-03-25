import {Component, OnInit} from '@angular/core';
import { Observable } from 'rxjs';
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent implements OnInit {
  title = 'Muzikum';
  isAuthenticated: boolean;

  constructor(private http: HttpClient) {}

  async ngOnInit() {

  }


}
