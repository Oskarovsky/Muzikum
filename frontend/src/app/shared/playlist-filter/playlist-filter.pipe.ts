import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'playlistFilter'
})
export class PlaylistFilterPipe implements PipeTransform {

  transform(value: any, ...args: any[]): any {
    return null;
  }

}
