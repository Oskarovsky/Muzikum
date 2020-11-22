import {FormControl, FormGroup, AbstractControl, ValidationErrors} from '@angular/forms';

export class PasswordValidator {

  // static OldPasswordMustBeCorrect(control: FormControl) {
  //   const invalid = false;
  //   if (control.value !== PasswordValidator.oldPW) {
  //     return { oldPasswordMustBeCorrect: true };
  //   }
  //   return null;
  // }

  static newIsNotOld(group: FormGroup) {
    const newPwd = group.controls.newPwd;
    if (group.controls.oldPwd.value === newPwd.value) {
      newPwd.setErrors({ newIsNotOld: true });
    }
    return null;
  }

  static matchPwds(control: AbstractControl) {
    const newPwd2 = control.get('newPwd');
    const confirmPwd2 = control.get('confirmPwd');
    if (newPwd2.value !== confirmPwd2.value) {
      return { pwdsDontMatch: true };
    }
    return null;
  }
}
