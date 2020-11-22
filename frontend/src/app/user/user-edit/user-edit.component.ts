import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { UserService } from 'src/app/services/user/user.service';
import {TokenStorageService} from '../../services/auth/token-storage.service';
import { MustMatch } from 'src/app/auth/register/MustMatch';
import {AlertService} from '../../services/alert/alert.service';
import {AuthService} from '../../services/auth/auth.service';
import {UserDto} from './user-dto';
import { User } from 'src/app/services/user/user';
import {Observable} from 'rxjs';

@Component({
  selector: 'app-user-edit',
  templateUrl: './user-edit.component.html',
  styleUrls: ['./user-edit.component.scss']
})
export class UserEditComponent implements OnInit {

  formGroup: FormGroup;
  loading = false;
  submitted = false;
  currentUser: any;
  userDto: UserDto = {
    id: '',
    username: '',
    firstName: '',
    city: '',
    facebookUrl: '',
    youtubeUlr: ''
  };


  constructor(private formBuilder: FormBuilder, private route: ActivatedRoute,
              private router: Router, private userService: UserService,
              private tokenStorage: TokenStorageService, private alertService: AlertService,
              private authService: AuthService
  ) { }

  ngOnInit() {
    if (this.tokenStorage.getToken()) {
      this.currentUser = this.tokenStorage.getUser();
      this.userDto.username = this.currentUser.username;
    }

    this.formGroup = this.formBuilder.group({
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
  get f() { return this.formGroup.controls; }

  onSubmit() {
    // reset alerts on submit
    this.alertService.clear();
    this.userService.updateUser(this.currentUser.id, this.formGroup).subscribe(
      data => {
        this.alertService.success('');
      }, error => {
        this.alertService.error('');
      }
    );

    // stop here if form is invalid
    if (this.formGroup.invalid) {
      return;
    }
    this.loading = true;
  }

  private updateUser(email: string) {
  }

}
