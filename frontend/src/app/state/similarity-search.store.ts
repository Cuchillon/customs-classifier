import { UserSearchResponse } from '../model/UserSearchResponse';
import { patchState, signalStore, withMethods, withState } from '@ngrx/signals';
import { inject } from '@angular/core';
import { DataOperationApiService } from '../services/data-operation-api.service';
import { rxMethod } from '@ngrx/signals/rxjs-interop';
import { pipe, switchMap, tap } from 'rxjs';
import { UserSearchRequest } from '../model/UserSearchRequest';
import { tapResponse } from '@ngrx/operators';

type SimilaritySearchState = {
  data: UserSearchResponse;
  isLoading: boolean;
};

const initialState: SimilaritySearchState = {
  data: { items: [] },
  isLoading: false
};

export const SimilaritySearchStore = signalStore(
  withState<SimilaritySearchState>(initialState),
  withMethods((store, dataOperationApiService = inject(DataOperationApiService)) => ({
    loadUserSearchResponse: rxMethod<UserSearchRequest>(
      pipe(
        tap(() => patchState(store, { isLoading: true })),
        switchMap(request => {
          return dataOperationApiService.searchData(request).pipe(
            tapResponse({
              next: response => patchState(store, { data: response }),
              error: console.error,
              finalize: () => patchState(store, { isLoading: false })
            })
          )
        })
      )
    )
  }))
);
