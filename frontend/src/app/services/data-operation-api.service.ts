import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { UserSearchRequest } from '../model/UserSearchRequest';
import { Observable, of } from 'rxjs';
import { UserSearchResponse } from '../model/UserSearchResponse';

@Injectable({
  providedIn: 'root',
})
export class DataOperationApiService {
  private http: HttpClient = inject(HttpClient);
  private path = '/api/v1';

  public searchData(request: UserSearchRequest): Observable<UserSearchResponse> {
    // return this.http.post<UserSearchResponse>(this.path, request);
    return of(this.userSearchResponse);
    //return of({ items:[] });
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
