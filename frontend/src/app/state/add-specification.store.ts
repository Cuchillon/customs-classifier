import { patchState, signalStore, withMethods, withState } from '@ngrx/signals';
import { SpecificationLoadStatus } from '../model/SpecificationLoadStatus';
import { inject } from '@angular/core';
import { DataOperationApiService } from '../services/data-operation-api.service';
import { rxMethod } from '@ngrx/signals/rxjs-interop';
import { pipe, switchMap, tap } from 'rxjs';
import { tapResponse } from '@ngrx/operators';
import { StoreMeta } from '../model/StoreMeta';
import { DialogService } from '../services/dialog.service';

type AddSpecificationState = {
  result: SpecificationLoadStatus;
};

const initialState: AddSpecificationState = {
  result: 'NOT_LOADED'
};

export const AddSpecificationStore = signalStore(
  withState<AddSpecificationState>(initialState),
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
    loadSpecification: rxMethod<{ meta: StoreMeta, file: File }>(
      pipe(
        tap(() => dialogService.showFullscreenLoader('Файл загружается...')),
        switchMap(request => {
          return dataOperationApiService.storeData(request.meta, request.file).pipe(
            tapResponse({
              next: () => {
                dialogService.showSuccess({
                  label: 'Статус загрузки',
                  content: 'Файл успешно загружен'
                });
                patchState(store, { result: 'SUCCESS' });
              },
              error: () => {
                dialogService.showError({
                  label: 'Статус загрузки',
                  content: 'Загрузка файла завершилась ошибкой'
                });
                patchState(store, { result: 'FAILURE' });
              },
              finalize: () => dialogService.hideFullscreenLoader()
            })
          )
        })
      )
    )
  }))
);
