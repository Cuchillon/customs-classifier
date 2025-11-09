import { ChangeDetectionStrategy, Component, input, OnDestroy, OnInit, output } from '@angular/core';
import { AsyncPipe } from "@angular/common";
import { FormArray, FormControl, ReactiveFormsModule, Validators } from "@angular/forms";
import { TuiButton, TuiError, TuiLabel, TuiTextfieldComponent } from "@taiga-ui/core";
import { TuiElasticContainer, TuiFieldErrorPipe, TuiTextarea } from "@taiga-ui/kit";
import { Subscription } from 'rxjs';

export type FilterProperties = {
  addingTitle: string;
  removingTitle: string;
  label: string;
};

export type ChangedFilters = {
  values: string[];
  valid: boolean;
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
export class ElasticFilter implements OnInit, OnDestroy {
  public filterProperties = input.required<FilterProperties>();
  protected readonly controlArray = new FormArray<FormControl>([]);
  protected changedFilters = output<ChangedFilters>();
  private subscription = new Subscription();

  ngOnInit() {
    this.subscription.add(
      this.controlArray.valueChanges.subscribe(() => {
        this.emitFilters();
      })
    );

    this.subscription.add(
      this.controlArray.statusChanges.subscribe(() => {
        this.emitFilters();
      })
    );
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  protected addFilter() {
    this.controlArray.push(
      new FormControl<string>('', { validators: Validators.required, nonNullable: true })
    );
  }

  protected removeFilter(index: number) {
    this.controlArray.removeAt(index);
  }

  private emitFilters() {
    this.changedFilters.emit({
      values: this.controlArray.value,
      valid: this.controlArray.valid,
    });
  }
}
