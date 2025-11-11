import { ChangeDetectionStrategy, Component, signal } from '@angular/core';
import { TuiAppearance, TuiError, TuiLabel, TuiTextfieldComponent } from "@taiga-ui/core";
import { TuiCardLarge } from "@taiga-ui/layout";
import { TuiFieldErrorPipe, TuiTextarea } from '@taiga-ui/kit';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
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

  protected readonly formGroup = new FormGroup({
    client: new FormControl<string>('', { validators: Validators.required, nonNullable: true }),
    specification: new FormControl<string>('', { validators: Validators.required, nonNullable: true })
  });

  private file = signal<File | null>(null);

  protected onFileChange(event: File | null) {
    this.file.set(event);
  }
}
