import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Observable } from 'rxjs';
import { Provider } from 'src/app/model/provider';

@Injectable({providedIn: 'root'})
export class ProviderService {

  public API = '//localhost:8080';
  public PROVIDER_API = this.API + '/providers';

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private http: HttpClient) {}

  getAll(): Observable<any> {
    return this.http.get<Provider[]>(this.PROVIDER_API + '/findAll');
  }

  getProvider(id: string): Observable<any> {
    return this.http.get<Provider[]>(this.PROVIDER_API + '/' + id);
  }

  createProvider(provider: object): Observable<object> {
    return this.http.post(this.PROVIDER_API + '/add', provider);
  }

}
