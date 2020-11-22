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
  passwordForm: any = {};

  ngOnInit() {
    this.currentUser = this.tokenStorage.getUser();
  }

  public onSubmit() {
    this.passwordForm.email = this.currentUser.email;
    this.authService.changeUserPassword(this.passwordForm).subscribe(
      data => {
        this.alertService.success('Hasło zostało zmienione. Sprawdź czy otrzymałeś wiadomość mailową z potwierdzeniem.');
      },
      err => {
        this.alertService.error('ERROR - Nie udało się zmienić hasła. Spróbuj ponownie.');
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
