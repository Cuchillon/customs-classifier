import { ChangeDetectionStrategy, Component, input, output, signal } from '@angular/core';
import { AsyncPipe } from "@angular/common";
import { FormControl, ReactiveFormsModule, Validators } from "@angular/forms";
import {
  TuiFile,
  TuiFileLike,
  TuiFileRejectedPipe,
  TuiFilesComponent,
  TuiInputFiles,
  TuiInputFilesDirective
} from "@taiga-ui/kit";
import { Observable, of, switchMap } from 'rxjs';

export const MAX_SIZE = 1024 * 1024;

export type FileInputProperties = {
  label: string;
  extension: string;
};

@Component({
  selector: 'app-file-input-field',
    imports: [
        AsyncPipe,
        ReactiveFormsModule,
        TuiFile,
        TuiFileRejectedPipe,
        TuiFilesComponent,
        TuiInputFiles,
        TuiInputFilesDirective
    ],
  templateUrl: './file-input-field.html',
  styleUrl: './file-input-field.less',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class FileInputField {
  public fileInputProperties = input.required<FileInputProperties>();
  protected maxSize = signal<number>(MAX_SIZE);

  protected readonly control = new FormControl<TuiFileLike | null>(
    null,
    Validators.required,
  );

  protected readonly loadedFiles$ = this.control.valueChanges.pipe(
    switchMap((file) => this.processFile(file)),
  );

  protected fileChanged = output<File | null>();

  private selectedFile: File | null = null;

  public removeFile(): void {
    this.control.reset();
    this.selectedFile = null;
    this.fileChanged.emit(this.selectedFile);
  }

  protected onFileInputChange(event: Event) {
    const input = event.target as HTMLInputElement;
    this.selectedFile = input.files?.[0] ?? null;
  }

  private processFile(file: TuiFileLike | null): Observable<TuiFileLike | null> {
    if (this.control.invalid || !file || (file.size && file.size > this.maxSize())) {
      this.selectedFile = null;
      this.fileChanged.emit(this.selectedFile);
      return of(null);
    }

    this.fileChanged.emit(this.selectedFile);
    return of(file);
  }
}
