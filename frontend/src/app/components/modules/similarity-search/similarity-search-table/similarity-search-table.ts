import { ChangeDetectionStrategy, Component, inject } from '@angular/core';
import { TuiAppearance } from "@taiga-ui/core";
import { TuiCardLarge } from "@taiga-ui/layout";
import {
  TuiTableDirective,
  TuiTableTbody,
  TuiTableTd,
  TuiTableTh,
  TuiTableThGroup,
  TuiTableTr
} from "@taiga-ui/addon-table";
import { SimilaritySearchStore } from '../../../../state/similarity-search.store';
import { LoadingSpinner } from '../../../shared/loading-spinner/loading-spinner';

@Component({
  selector: 'app-similarity-search-table',
  imports: [
    TuiAppearance,
    TuiCardLarge,
    TuiTableDirective,
    TuiTableTbody,
    TuiTableTd,
    TuiTableTh,
    TuiTableThGroup,
    TuiTableTr,
    LoadingSpinner
  ],
  templateUrl: './similarity-search-table.html',
  styleUrl: './similarity-search-table.less',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class SimilaritySearchTable {
  protected readonly store = inject(SimilaritySearchStore);
}
