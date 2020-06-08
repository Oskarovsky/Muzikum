import { Injectable } from '@angular/core';
import {environment} from '../../../environments/environment';

const API: string = environment.serverUrl;

@Injectable({
  providedIn: 'root'
})
export class TokenConfirmationService {

  constructor() { }
}
