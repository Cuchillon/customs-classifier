import { UserSearchResponse } from '../model/UserSearchResponse';
import { patchState, signalStore, withMethods, withState } from '@ngrx/signals';
import { inject } from '@angular/core';
import { DataOperationApiService } from '../services/data-operation-api.service';
import { rxMethod } from '@ngrx/signals/rxjs-interop';
import { pipe, switchMap, tap } from 'rxjs';
import { UserSearchRequest } from '../model/UserSearchRequest';
import { tapResponse } from '@ngrx/operators';
import { DialogService } from '../services/dialog.service';
import { escapeHtml } from '../utils/util-html';

type SimilaritySearchState = {
  data: UserSearchResponse;
  isLoading: boolean;
  isLoaded: boolean;
};

const initialState: SimilaritySearchState = {
  data: { items: [] },
  isLoading: false,
  isLoaded: false
};

export const SimilaritySearchStore = signalStore(
  withState<SimilaritySearchState>(initialState),
  withMethods(
    (
      store,
      dataOperationApiService = inject(DataOperationApiService),
      dialogService = inject(DialogService)
    ) =>
  ({
    clearState() {
      patchState(store, { ...initialState })
    },
    loadUserSearchResponse: rxMethod<UserSearchRequest>(
      pipe(
        tap(() => patchState(store, { isLoading: true })),
        switchMap(request => {
          return dataOperationApiService.searchData(request).pipe(
            tapResponse({
              next: response =>
                patchState(store, { data: response, isLoading: false, isLoaded: true }),
              error: (error: any) => {
                const status = error['status'];
                const message = error['statusText'];
                dialogService.showError({
                  label: 'Статус поиска',
                  content: `Поиск завершился ошибкой<br>
                            Статус ошибки: ${escapeHtml(String(status))}<br>
                            Причина: ${escapeHtml(String(message))}`
                });
                patchState(store, { ...initialState });
              }
            })
          )
        })
      )
    )
  }))
);
