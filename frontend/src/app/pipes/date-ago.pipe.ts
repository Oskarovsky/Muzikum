import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'dateAgo',
  pure: true
})
export class DateAgoPipe implements PipeTransform {

  transform(value: any, ...args: any[]): any {
    if (value) {
      const seconds = Math.floor((+new Date() - +new Date(value)) / 1000);
      if (seconds < 29) { // less than 30 seconds ago will show as 'Just now'
        return 'Przed chwilą';
      }
      const intervals = {
        rok: 31536000,
        miesiac: 2592000,
        tydzien: 604800,
        dni: 86400,
        godzin: 3600,
        minut: 60,
        sekund: 1
      };
      let counter;
      // tslint:disable-next-line:forin
      for (const i in intervals) {
        counter = Math.floor(seconds / intervals[i]);
        if (counter > 0) {
          if (counter <= 4) {
            return counter + ' ' + i + ' temu'; // singular (1 day ago)
          } else {
            return counter + ' ' + i + ' temu'; // plural (2 days ago)
          }
        }
      }
    }
    return value;
  }
}
