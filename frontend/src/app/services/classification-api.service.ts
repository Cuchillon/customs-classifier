import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ClassificationTaskMeta } from '../model/ClassificationTaskMeta';
import { ClassificationTaskCreateResponse } from '../model/ClassificationTaskCreateResponse';
import { ClassificationTaskStatusResponse } from '../model/ClassificationTaskStatusResponse';

@Injectable({
  providedIn: 'root',
})
export class ClassificationApiService {
  private http: HttpClient = inject(HttpClient);
  private path = '/api/v1';

  public createTask(meta: ClassificationTaskMeta, file: File): Observable<ClassificationTaskCreateResponse> {
    const formData = new FormData();
    formData.append('meta', new Blob([JSON.stringify(meta)], {
      type: 'application/json'
    }));
    formData.append('data', file, file.name);
    return this.http.post<ClassificationTaskCreateResponse>(`${this.path}/classify`, formData);
  }

  public getTaskStatus(id: number): Observable<ClassificationTaskStatusResponse> {
    return this.http.get<ClassificationTaskStatusResponse>(`${this.path}/classify/${id}/status`);
  }

  public download(id: number): Observable<Blob> {
    return this.http.get(`${this.path}/classify/${id}/download`, { responseType: 'blob' });
  }
}
