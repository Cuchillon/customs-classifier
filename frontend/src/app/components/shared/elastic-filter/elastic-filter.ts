import { ChangeDetectionStrategy, Component, input } from '@angular/core';
import { AsyncPipe } from "@angular/common";
import { FormArray, FormControl, ReactiveFormsModule, Validators } from "@angular/forms";
import { TuiButton, TuiError, TuiLabel, TuiTextfieldComponent } from "@taiga-ui/core";
import { TuiElasticContainer, TuiFieldErrorPipe, TuiTextarea } from "@taiga-ui/kit";

export type FilterProperties = {
  addingTitle: string;
  removingTitle: string;
  label: string;
};

@Component({
  selector: 'app-elastic-filter',
    imports: [
        AsyncPipe,
        ReactiveFormsModule,
        TuiButton,
        TuiElasticContainer,
        TuiError,
        TuiFieldErrorPipe,
        TuiLabel,
        TuiTextarea,
        TuiTextfieldComponent
    ],
  templateUrl: './elastic-filter.html',
  styleUrl: './elastic-filter.less',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ElasticFilter {
  public filterProperties = input.required<FilterProperties>();
  protected readonly controlArray = new FormArray<FormControl>([]);

  protected addFilter() {
    this.controlArray.push(
      new FormControl<string>(
        '',
        Validators.required,
      )
    );
  }

  protected removeFilter(index: number) {
    this.controlArray.removeAt(index);
  }
}
