import { ChangeDetectionStrategy, Component, input } from '@angular/core';
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
        TuiTableTr
    ],
  templateUrl: './similarity-search-table.html',
  styleUrl: './similarity-search-table.less',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class SimilaritySearchTable {
  public userSearchResponse = input.required<UserSearchResponse>();
}
