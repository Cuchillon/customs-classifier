import { ChangeDetectionStrategy, Component } from '@angular/core';
import { TuiAppearance } from '@taiga-ui/core';
import { TuiCardLarge } from '@taiga-ui/layout';

@Component({
  selector: 'app-similarity-search-empty',
  imports: [
    TuiAppearance,
    TuiCardLarge
  ],
  templateUrl: './similarity-search-empty.html',
  styleUrl: './similarity-search-empty.less',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class SimilaritySearchEmpty {

}
