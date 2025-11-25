import { patchState, signalStore, withMethods, withState } from '@ngrx/signals';
import { SpecificationLoadStatus } from '../model/SpecificationLoadStatus';
import { inject } from '@angular/core';
import { DataOperationApiService } from '../services/data-operation-api.service';
import { rxMethod } from '@ngrx/signals/rxjs-interop';
import { pipe, switchMap, tap } from 'rxjs';
import { tapResponse } from '@ngrx/operators';
import { StoreMeta } from '../model/StoreMeta';

type AddSpecificationState = {
  result: SpecificationLoadStatus;
  isLoading: boolean;
};

const initialState: AddSpecificationState = {
  result: 'NOT_LOADED',
  isLoading: false
};

export const AddSpecificationStore = signalStore(
  withState<AddSpecificationState>(initialState),
  withMethods((store, dataOperationApiService = inject(DataOperationApiService)) => ({
    clearState() {
      patchState(store, { ...initialState })
    },
    loadSpecification: rxMethod<{ meta: StoreMeta, file: File }>(
      pipe(
        tap(() => patchState(store, { isLoading: true })),
        switchMap(request => {
          return dataOperationApiService.storeData(request.meta, request.file).pipe(
            tapResponse({
              next: () => patchState(store, { result: 'SUCCESS' }),
              error: () => patchState(store, { result: 'FAILURE' }),
              finalize: () => patchState(store, { isLoading: false })
            })
          )
        })
      )
    )
  }))
);
