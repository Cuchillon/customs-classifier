import { ChangeDetectionStrategy, Component } from '@angular/core';
import { TuiAppearance, TuiError, TuiTextfield } from '@taiga-ui/core';
import { TuiFieldErrorPipe, TuiInputNumber, TuiTextarea } from '@taiga-ui/kit';
import { TuiCardLarge } from '@taiga-ui/layout';
import { FormControl, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { AsyncPipe } from '@angular/common';
import { SimilaritySearchTable } from './similarity-search-table/similarity-search-table';
import { ButtonBlock } from '../../shared/button-block/button-block';
import { ElasticFilter } from '../../shared/elastic-filter/elastic-filter';

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
  protected readonly descriptionKControl = new FormControl<string>(
    '',
    Validators.required,
  );
  protected readonly topKControl = new FormControl<number>(
    4,
    Validators.required,
  );
  protected readonly similarityThresholdControl = new FormControl<number>(
    80,
    Validators.required,
  );

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
