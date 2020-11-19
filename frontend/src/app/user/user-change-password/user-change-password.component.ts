import { Component, OnInit } from '@angular/core';
import {AuthService} from '../../services/auth/auth.service';
import {FormGroup, AbstractControl, Validators, FormBuilder} from '@angular/forms';
import { PasswordValidator } from './password-validator';

@Component({
  selector: 'app-user-change-password',
  templateUrl: './user-change-password.component.html',
  styleUrls: ['./user-change-password.component.scss']
})
export class UserChangePasswordComponent implements OnInit {

  constructor(private authService: AuthService,
              private formBuilder: FormBuilder) {
    this.form1 = formBuilder.group({
      oldPwd: ['', Validators.required, PasswordValidator.shouldBe1234],
      newPwd: ['', Validators.required],
      confirmPwd: ['', Validators.required]
    }, {
      validator: PasswordValidator.matchPwds
    });
  }

  form1: FormGroup;

  oldPassword: AbstractControl;
  newPassword: AbstractControl;
  confirmPassword: AbstractControl;

  passwordForm: FormGroup;
  isSuccessful = false;

  ngOnInit() {

    this.oldPassword = this.form1.controls.current;
    this.newPassword = this.form1.controls.newPW;
    this.confirmPassword = this.form1.controls.confirm;
  }

  public onSubmit() {
    this.authService.changeUserPassword(this.passwordForm).subscribe(
      data => {
        this.isSuccessful = true;
      },
      error => {
        this.isSuccessful = false;
      }
    );
  }

  get oldPwd() {
    return this.form1.get('oldPwd');
  }

  get newPwd() {
    return this.form1.get('newPwd');
  }

  get confirmPwd() {
    return this.form1.get('confirmPwd');
  }

}
