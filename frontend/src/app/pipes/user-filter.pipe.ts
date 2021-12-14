import {Pipe, PipeTransform} from '@angular/core';
import {User} from '../services/user/user';

@Pipe({
  name: 'userFilter'
})
export class UserFilterPipe implements PipeTransform {

  transform(users: User[], text: string): User[] {
    if (text == null || text === '') {
      return null;
    }
    return users.filter(n => n.username.includes(text));
  }
}
