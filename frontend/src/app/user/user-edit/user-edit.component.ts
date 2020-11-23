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
import {first} from 'rxjs/operators';

@Component({
  selector: 'app-user-edit',
  templateUrl: './user-edit.component.html',
  styleUrls: ['./user-edit.component.scss']
})
export class UserEditComponent implements OnInit {

  currentUser: any;
  form: FormGroup;
  id: string;
  loading = false;
  submitted = false;


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
      firstName: '',
      facebookUrl: '',
      youtubeUrl: '',
      city: '',
    });

    this.userService.getUserById(this.currentUser.id)
      .pipe(first())
      .subscribe(x => this.form.patchValue(x));
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
    this.updateUser();
  }

  private updateUser() {
    this.userService.updateUser(this.currentUser.id, this.form.value)
      .pipe(first())
      .subscribe({
        next: () => {
          this.alertService.success('User updated', { keepAfterRouteChange: true });
          this.router.navigate(['../'], { relativeTo: this.route });
        },
        error: error => {
          this.alertService.error(error);
          this.loading = false;
        }
      });
  }

}
