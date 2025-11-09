import { ChangeDetectionStrategy, Component, inject } from '@angular/core';
import { TuiAppearance, TuiError, TuiTextfield } from '@taiga-ui/core';
import { TuiFieldErrorPipe, TuiInputNumber, TuiTextarea } from '@taiga-ui/kit';
import { TuiCardLarge } from '@taiga-ui/layout';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { AsyncPipe } from '@angular/common';
import { SimilaritySearchTable } from './similarity-search-table/similarity-search-table';
import { ButtonBlock } from '../../shared/button-block/button-block';
import { ElasticFilter } from '../../shared/elastic-filter/elastic-filter';
import { UserSearchResponse } from '../../../model/UserSearchResponse';
import { DataOperationApiService } from '../../../services/data-operation-api.service';

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
  templateUrl: './similarity-search.html',
  styleUrl: './similarity-search.less',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class SimilaritySearch {
  private readonly dataOperationApiService = inject(DataOperationApiService);

  protected readonly formGroup = new FormGroup({
    description: new FormControl<string>('', Validators.required),
    topK: new FormControl<number>(DEFAULT_TOP_K, Validators.required),
    similarityThreshold: new FormControl<number>(DEFAULT_SIMILARITY_THRESHOLD, Validators.required)
  });

  protected submitForm() {
    if (this.formGroup.valid) {
      // TODO
      console.log('Form submit');
    }
  }

  protected clearForm() {
    this.formGroup.controls.description.reset();
    this.formGroup.controls.topK.setValue(DEFAULT_TOP_K);
    this.formGroup.controls.similarityThreshold.setValue(DEFAULT_SIMILARITY_THRESHOLD);
  }

  protected userSearchResponse: UserSearchResponse = {
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
