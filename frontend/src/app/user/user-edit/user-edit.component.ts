import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { UserService } from 'src/app/services/user/user.service';
import {TokenStorageService} from '../../services/auth/token-storage.service';
import { MustMatch } from 'src/app/auth/register/MustMatch';
import {AlertService} from '../../services/alert/alert.service';
import {AuthService} from '../../services/auth/auth.service';

@Component({
  selector: 'app-user-edit',
  templateUrl: './user-edit.component.html',
  styleUrls: ['./user-edit.component.scss']
})
export class UserEditComponent implements OnInit {

  form: FormGroup;
  loading = false;
  submitted = false;
  currentUser: any;


  constructor(private formBuilder: FormBuilder, private route: ActivatedRoute,
              private router: Router, private userService: UserService,
              private tokenStorage: TokenStorageService, private alertService: AlertService,
              private authService: AuthService
  ) { }

  ngOnInit() {
    if (this.tokenStorage.getToken()) {
      this.currentUser = this.tokenStorage.getUser();
    }

    this.form = this.formBuilder.group({
      title: ['', Validators.required],
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      role: ['', Validators.required],
    }, {
      validator: MustMatch('password', 'confirmPassword')
    });
  }

  // convenience getter for easy access to form fields
  get f() { return this.form.controls; }

  onSubmit() {
    // reset alerts on submit
    this.alertService.clear();

    // stop here if form is invalid
    if (this.form.invalid) {
      return;
    }
    this.loading = true;
  }

  private updateUser(email: string) {
  }

}
