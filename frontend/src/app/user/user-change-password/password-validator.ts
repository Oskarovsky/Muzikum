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
    const newPW = group.controls.newPW;
    if (group.controls.current.value === newPW.value) {
      newPW.setErrors({ newIsNotOld: true });
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
