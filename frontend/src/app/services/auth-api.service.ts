import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { ApiKeyRequest } from '../model/ApiKeyRequest';
import { Observable } from 'rxjs';
import { ApiKeyResponse } from '../model/ApiKeyResponse';

@Injectable({
  providedIn: 'root',
})
export class AuthApiService {
  private http: HttpClient = inject(HttpClient);
  private path = '/api/v1/api-key';

  public getApiKey(
    username: string,
    password: string,
    request: ApiKeyRequest
  ): Observable<ApiKeyResponse> {
    const headers = this.getBasicAuthHeaders(username, password);
    return this.http.post<ApiKeyResponse>(`${this.path}/generate`, request, { headers });
  }

  private getBasicAuthHeaders(username: string, password: string): HttpHeaders {
    const credentials = btoa(`${username}:${password}`);
    return  new HttpHeaders({
      Authorization: `Basic ${credentials}`
    });
  }
}
