import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth/auth.service';
import {FormBuilder, FormGroup, Validators, FormControl} from '@angular/forms';
import {MustMatch} from './MustMatch';
import {AlertService} from '../../services/alert/alert.service';
import {FACEBOOK_AUTH_URL} from '../../../assets/constants/app.const';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  form: any = {};
  isSuccessful = false;
  registerForm: FormGroup;
  submitted = false;
  facebookAuthUrl = FACEBOOK_AUTH_URL;

  constructor(private authService: AuthService,
              private alertService: AlertService,
              private formBuilder: FormBuilder) { }

  ngOnInit() {
    this.registerForm = this.formBuilder.group({
      username: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword: ['', Validators.required]
    }, {
      validator: MustMatch('password', 'confirmPassword')
    });
  }

  onSubmit() {
    this.authService.register(this.form).subscribe(
      data => {
        this.isSuccessful = true;
      },
      err => {
        this.alertService.error('Wystąpił błąd podczas procesu rejestracji. Spróbuj ponownie!');
      }
    );
  }
}
