import { Component, OnInit } from '@angular/core';
import {AuthService} from '../../services/auth/auth.service';
import {FormGroup, AbstractControl, Validators, FormBuilder} from '@angular/forms';
import { PasswordValidator } from './password-validator';
import {AlertService} from '../../services/alert/alert.service';
import {PasswordChangeDto} from '../../services/auth/password-change-dto';
import {TokenStorageService} from '../../services/auth/token-storage.service';

@Component({
  selector: 'app-user-change-password',
  templateUrl: './user-change-password.component.html',
  styleUrls: ['./user-change-password.component.scss']
})
export class UserChangePasswordComponent implements OnInit {

  constructor(private authService: AuthService,
              private formBuilder: FormBuilder,
              private alertService: AlertService,
              private tokenStorage: TokenStorageService) {
    this.form1 = formBuilder.group({
      oldPwd: ['', Validators.required],
      newPwd: ['', Validators.required],
      confirmPwd: ['', Validators.required]
    }, {
      validator: PasswordValidator.matchPwds
    });
  }

  currentUser: any;
  form1: FormGroup;

  oldPassword: AbstractControl;
  newPassword: AbstractControl;
  confirmPassword: AbstractControl;

  passwordForm: FormGroup;
  isSuccessful = false;
  passwordDto: PasswordChangeDto;

  ngOnInit() {
    this.currentUser = this.tokenStorage.getUser();
    this.oldPassword = this.form1.controls.current;
    this.newPassword = this.form1.controls.newPW;
    this.confirmPassword = this.form1.controls.confirm;
  }

  public onSubmit() {
    this.passwordDto.oldPassword = this.oldPassword.value;
    this.passwordDto.newPassword = this.newPassword.value;
    this.passwordDto.email = this.currentUser.email;
    this.authService.changeUserPassword(this.passwordDto).subscribe(
      data => {
        this.isSuccessful = true;
      },
      error => {
        this.isSuccessful = false;
        this.alertService.error('ERROR - Cannot change password.');
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
