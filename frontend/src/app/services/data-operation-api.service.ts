import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { UserSearchRequest } from '../model/UserSearchRequest';
import { Observable, of } from 'rxjs';
import { UserSearchResponse } from '../model/UserSearchResponse';
import { StoreMeta } from '../model/StoreMeta';

@Injectable({
  providedIn: 'root',
})
export class DataOperationApiService {
  private http: HttpClient = inject(HttpClient);
  private path = '/api/v1';

  public searchData(request: UserSearchRequest): Observable<UserSearchResponse> {
    return this.http.post<UserSearchResponse>(`${this.path}/search`, request);
  }

  public storeData(meta: StoreMeta, file: File): Observable<void> {
    const formData = new FormData();
    formData.append('meta', new Blob([JSON.stringify(meta)], {
      type: 'application/json'
    }));
    formData.append('data', file, file.name);
    return this.http.post<void>(`${this.path}/store`, formData);
  }
}
