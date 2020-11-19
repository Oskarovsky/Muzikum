import { Component, OnInit } from '@angular/core';
import {AuthService} from '../../services/auth/auth.service';
import {FormGroup, AbstractControl, Validators, FormBuilder} from '@angular/forms';

@Component({
  selector: 'app-user-change-password',
  templateUrl: './user-change-password.component.html',
  styleUrls: ['./user-change-password.component.scss']
})
export class UserChangePasswordComponent implements OnInit {

  constructor(private authService: AuthService,
              private formBuilder: FormBuilder) {
  }

  form1: FormGroup;

  oldPassword: AbstractControl;
  newPassword: AbstractControl;
  confirmPassword: AbstractControl;

  passwordForm: FormGroup;
  isSuccessful = false;

  newIsNotOld(group: FormGroup) {
    const newPassword = group.controls.newPW;
    if (group.controls.current.value === newPassword.value) {
      newPassword.setErrors({ newIsNotOld: true });
    }
    return null;
  }

  matchPasswords() {
    const newPassword = this.newPassword;
    const confirmNewPassword = this.confirmPassword;
    if (newPassword.value !== confirmNewPassword.value) {
      return { passwordsDontMatch: true };
    }
    return null;
  }

  ngOnInit() {
    this.form1 = this.formBuilder.group({
      oldPassword: ['', Validators.required],
      newPassword: ['', Validators.required],
      confirmNewPassword: ['', Validators.required]
    }, {
      validator: this.matchPasswords
    });
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

}
