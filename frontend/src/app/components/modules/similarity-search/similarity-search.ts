import { ChangeDetectionStrategy, Component, inject, signal, viewChildren } from '@angular/core';
import { TuiAppearance, TuiError, TuiTextfield } from '@taiga-ui/core';
import { TuiFieldErrorPipe, TuiInputNumber, TuiTextarea } from '@taiga-ui/kit';
import { TuiCardLarge } from '@taiga-ui/layout';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { AsyncPipe } from '@angular/common';
import { SimilaritySearchTable } from './similarity-search-table/similarity-search-table';
import { ButtonBlock } from '../../shared/button-block/button-block';
import { ChangedFilters, ElasticFilter } from '../../shared/elastic-filter/elastic-filter';
import { SimilaritySearchStore } from '../../../state/similarity-search.store';
import { UserSearchRequest } from '../../../model/UserSearchRequest';

const DEFAULT_TOP_K = 4;
const DEFAULT_SIMILARITY_THRESHOLD = 90;

@Component({
  selector: 'app-similarity-search',
  imports: [
    AsyncPipe,
    TuiTextfield,
    TuiTextarea,
    TuiCardLarge,
    TuiAppearance,
    TuiInputNumber,
    ReactiveFormsModule,
    TuiError,
    TuiFieldErrorPipe,
    FormsModule,
    SimilaritySearchTable,
    ButtonBlock,
    ElasticFilter
  ],
  providers: [
    SimilaritySearchStore
  ],
  templateUrl: './similarity-search.html',
  styleUrl: './similarity-search.less',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class SimilaritySearch {
  protected readonly store = inject(SimilaritySearchStore);

  protected readonly formGroup = new FormGroup({
    description: new FormControl<string>('', { validators: Validators.required, nonNullable: true }),
    topK: new FormControl<number>(DEFAULT_TOP_K, { validators: Validators.required, nonNullable: true }),
    similarityThreshold: new FormControl<number>(DEFAULT_SIMILARITY_THRESHOLD, {
      validators: Validators.required, nonNullable: true
    })
  });

  protected clientFilters = signal<ChangedFilters>({ values: [], valid: true });
  protected specFilters = signal<ChangedFilters>({ values: [], valid: true });

  private filterElements = viewChildren(ElasticFilter);

  protected submitForm() {
    if (this.formGroup.valid && this.clientFilters().valid && this.specFilters().valid) {
      const request: UserSearchRequest = {
        query: this.formGroup.controls.description.value,
        topK: this.formGroup.controls.topK.value,
        similarityThreshold: this.formGroup.controls.similarityThreshold.value / 100
      };
      if (this.clientFilters().values.length > 0 || this.specFilters().values.length > 0) {
        request['meta'] = {
          clients: this.clientFilters().values,
          specifications: this.specFilters().values
        }
      }
      this.store.loadUserSearchResponse(request);
    }
  }

  protected clearForm() {
    this.formGroup.controls.description.reset();
    this.formGroup.controls.topK.setValue(DEFAULT_TOP_K);
    this.formGroup.controls.similarityThreshold.setValue(DEFAULT_SIMILARITY_THRESHOLD);
    this.filterElements().forEach(element => element.clearFilters());
    this.store.clearState();
  }
}
