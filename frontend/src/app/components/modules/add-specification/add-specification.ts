import { ChangeDetectionStrategy, Component } from '@angular/core';
import { TuiAppearance, TuiError, TuiLabel, TuiTextfieldComponent } from "@taiga-ui/core";
import { TuiCardLarge } from "@taiga-ui/layout";
import { TuiFieldErrorPipe, TuiTextarea } from '@taiga-ui/kit';
import { FormControl, ReactiveFormsModule, Validators } from '@angular/forms';
import { AsyncPipe } from '@angular/common';
import { ButtonBlock } from '../../shared/button-block/button-block';
import { FileInputField } from '../../shared/file-input-field/file-input-field';

@Component({
  selector: 'app-add-specification',
  imports: [
    TuiAppearance,
    TuiCardLarge,
    ReactiveFormsModule,
    AsyncPipe,
    TuiError,
    TuiFieldErrorPipe,
    TuiLabel,
    TuiTextarea,
    TuiTextfieldComponent,
    ButtonBlock,
    FileInputField
  ],
  templateUrl: './add-specification.html',
  styleUrl: './add-specification.less',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class AddSpecification {
  protected readonly clientControl = new FormControl<string>(
    '',
    Validators.required,
  );
  protected readonly specificationControl = new FormControl<string>(
    '',
    Validators.required,
  );
}
