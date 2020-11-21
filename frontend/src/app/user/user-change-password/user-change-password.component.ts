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
    this.formGroup = formBuilder.group({
      oldPwd: ['', Validators.required],
      newPwd: ['', Validators.required],
      confirmPwd: ['', Validators.required]
    }, {
      validator: PasswordValidator.matchPwds
    });
  }

  currentUser: any;
  formGroup: FormGroup;

  oldPassword: AbstractControl;
  newPassword: AbstractControl;
  confirmPassword: AbstractControl;

  passwordForm: any = {};
  isSuccessful = false;
  passwordDto: PasswordChangeDto;

  ngOnInit() {
    this.currentUser = this.tokenStorage.getUser();
    this.oldPassword = this.formGroup.controls.oldPwd;
    this.newPassword = this.formGroup.controls.newPwd;
    this.confirmPassword = this.formGroup.controls.confirmPwd;
  }

  public onSubmit() {
    this.passwordForm.email = this.currentUser.email;
    this.authService.changeUserPassword(this.passwordForm).subscribe(
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
    return this.formGroup.get('oldPwd');
  }

  get newPwd() {
    return this.formGroup.get('newPwd');
  }

  get confirmPwd() {
    return this.formGroup.get('confirmPwd');
  }

}
