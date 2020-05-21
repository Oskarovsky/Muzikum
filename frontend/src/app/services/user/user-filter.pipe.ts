import { Pipe, PipeTransform } from '@angular/core';
import {Playlist} from '../../playlists/playlist/model/playlist';
import {User} from './user';

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
