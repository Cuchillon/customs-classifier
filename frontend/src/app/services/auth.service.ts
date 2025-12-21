import { inject, Injectable, signal } from '@angular/core';
import { AuthApiService } from './auth-api.service';
import { ApiKeyResponse } from '../model/ApiKeyResponse';
import { ApiKeyRequest } from '../model/ApiKeyRequest';
import { map } from 'rxjs';
import { DialogService } from './dialog.service';
import { escapeHtml } from '../utils/util-html';
import { Router } from '@angular/router';

const STORAGE_AUTH_KEY = "auth-data";
const STORE_SCOPE = "store:all";
const TTL_SECONDS = 86400;

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private authApiService = inject(AuthApiService);
  private dialogService = inject(DialogService);
  private router = inject(Router);
  private _isAuthenticated = signal<boolean>(this.isApiKeyStored());

  public isAuthenticated = this._isAuthenticated.asReadonly();

  public login(username: string, password: string) {
    const apiKeyRequest: ApiKeyRequest = {
      scopes: [STORE_SCOPE],
      ttlSeconds: TTL_SECONDS
    };
    this.authApiService.getApiKey(username, password, apiKeyRequest).pipe(
      map(response => JSON.stringify(response))
    ).subscribe({
      next: response => {
        localStorage.setItem(STORAGE_AUTH_KEY, response);
        this._isAuthenticated.set(true);
        this.router.navigate(["/"]).then(r => console.log('User authorized'));
      },
      error: error => {
        this._isAuthenticated.set(false);
        localStorage.clear();
        const status = error['status'];
        const message = error['statusText'];
        this.dialogService.showError({
          label: 'Авторизация',
          content: `Не удалось авторизоваться<br>
                    Статус ошибки: ${escapeHtml(String(status))}<br>
                    Причина: ${escapeHtml(String(message))}`
        });
      }
    })
  }

  private isApiKeyStored(): boolean {
    const authData = localStorage.getItem(STORAGE_AUTH_KEY);

    if (!authData) {
      return false;
    } else {
      try {
        const parsed = JSON.parse(authData) as ApiKeyResponse;
        return !!parsed.rawKey && this.isApiKeyValid(parsed.expiresAt);
      } catch (e) {
        console.log(`Failed to get auth data from local storage`);
        localStorage.clear();
        return false;
      }
    }
  }

  private isApiKeyValid(expireAtString: string): boolean {
    const expiresAt = new Date(expireAtString);
    const now = new Date();

    if (isNaN(expiresAt.getTime())) {
      console.log(`Invalid expiresAt date string: ${expireAtString}`);
      localStorage.clear();
      return false;
    }

    return expiresAt > now;
  }
}
