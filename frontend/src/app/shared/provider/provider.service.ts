import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({providedIn: 'root'})
export class ProviderService {

  public API = '//localhost:8080';
  public PROVIDER_API = this.API + '/providers';

  constructor(private http: HttpClient) {
  }

  getAll(): Observable<any> {
    return this.http.get(this.PROVIDER_API + '/findAll');
  }

  getProvider(id: string) {
    return this.http.get(this.PROVIDER_API + '/' + id);
  }

  createProvider(provider: object): Observable<object> {
    return this.http.post(this.PROVIDER_API + '/add', provider);
  }

}
