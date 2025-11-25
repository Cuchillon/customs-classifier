import { ChangeDetectionStrategy, Component, DestroyRef, effect, inject, signal, viewChild } from '@angular/core';
import { TuiAppearance, TuiDialogService, TuiError, TuiLabel, TuiTextfieldComponent } from "@taiga-ui/core";
import { TuiCardLarge } from "@taiga-ui/layout";
import { TuiFieldErrorPipe, TuiTextarea } from '@taiga-ui/kit';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { AsyncPipe } from '@angular/common';
import { ButtonBlock } from '../../shared/button-block/button-block';
import { FileInputField } from '../../shared/file-input-field/file-input-field';
import { AddSpecificationStore } from '../../../state/add-specification.store';
import { StoreMeta } from '../../../model/StoreMeta';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';

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
  providers: [
    AddSpecificationStore
  ],
  templateUrl: './add-specification.html',
  styleUrl: './add-specification.less',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class AddSpecification {
  private readonly destroyRef = inject(DestroyRef);
  protected readonly store = inject(AddSpecificationStore);
  private readonly dialogs = inject(TuiDialogService);

  protected readonly formGroup = new FormGroup({
    client: new FormControl<string>('', { validators: Validators.required, nonNullable: true }),
    specification: new FormControl<string>('', { validators: Validators.required, nonNullable: true })
  });

  private file = signal<File | null>(null);

  private fileInputField = viewChild.required(FileInputField);

  constructor() {
    effect(() => {
      if (this.store.result() === 'SUCCESS') {
        this.showSuccess();
        this.store.clearState();
        this.clear();
      } else if (this.store.result() === 'FAILURE') {
        this.showError();
        this.store.clearState();
      }
    });
  }

  protected onFileChange(event: File | null) {
    this.file.set(event);
  }

  protected onSave() {
    if (this.formGroup.valid && this.file()) {
      const meta: StoreMeta = {
        client: this.formGroup.controls.client.value,
        specification: this.formGroup.controls.specification.value
      };
      this.store.loadSpecification({ meta: meta, file: this.file()! });
    }
  }

  protected clear() {
    this.formGroup.reset();
    this.fileInputField().removeFile();
  }

  protected showSuccess(): void {
    this.dialogs
      .open('Файл успешно загружен', {
        label: 'Статус загрузки \u2705',
        size: 's'
      }).pipe(
      takeUntilDestroyed(this.destroyRef)
    )
      .subscribe();
  }

  protected showError(): void {
    this.dialogs
      .open('Загрузка файла завершилась ошибкой', {
        label: 'Статус загрузки \u274C',
        size: 's'
      }).pipe(
        takeUntilDestroyed(this.destroyRef)
    )
      .subscribe();
  }
}
