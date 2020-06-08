import { Component, OnInit } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Router, ActivatedRoute} from '@angular/router';
import { TokenConfirmationService } from 'src/app/services/auth/token-confirmation.service';
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-token-confirmation',
  templateUrl: './token-confirmation.component.html',
  styleUrls: ['./token-confirmation.component.scss']
})
export class TokenConfirmationComponent implements OnInit {

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  public token: string;
  public token2: string;
  public url: string;
  public result: string;
  sub: Subscription;

  constructor(private http: HttpClient,
              private router: Router,
              private activatedRoute: ActivatedRoute,
              private tokenService: TokenConfirmationService) { }

  ngOnInit() {
    this.getTokenConfirmation();
  }

  public getTokenConfirmation() {
    this.sub = this.activatedRoute.params.subscribe(params => {
      const token = params.token;
      this.token2 = token;
      if (token) {
        this.tokenService.tokenConfirm(token).subscribe(
          response => {
            this.result = response;
          },
          error => {
            alert('An error with token confirmation');
          }
        );
      }
    });
  }

}
