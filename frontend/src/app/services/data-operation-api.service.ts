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
    console.log(JSON.stringify(request));
    return this.http.post<UserSearchResponse>(`${this.path}/search`, request);
    // return of(this.userSearchResponse);
    // return of({ items:[] });
  }

  public storeData(meta: StoreMeta, file: File): Observable<void> {
    const formData = new FormData();
    formData.append('meta', new Blob([JSON.stringify(meta)], {
      type: 'application/json'
    }));
    formData.append('data', file, file.name);
    return this.http.post<void>(`${this.path}/store`, formData);
  }

  // Mock
  private userSearchResponse: UserSearchResponse = {
    items: [
      {
        code: '8420108000',
        text: 'Тестораскаточная машина',
        score: 0.9704168532043695,
        meta: {
          client: 'Machines',
          specification: 'equipment'
        }
      },
      {
        code: '8438809900',
        text: 'Формовочная машина',
        score: 0.9304168532043695,
        meta: {
          client: 'Machines',
          specification: 'equipment'
        }
      },
      {
        code: '8438809900',
        text: 'Фаршевый насос',
        score: 0.9104168532043695,
        meta: {
          client: 'Machines',
          specification: 'equipment'
        }
      }
    ]
  };
}
