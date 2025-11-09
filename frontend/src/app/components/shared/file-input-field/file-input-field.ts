import { ChangeDetectionStrategy, Component, input } from '@angular/core';
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
import { finalize, map, Observable, of, Subject, switchMap, timer } from 'rxjs';

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

  protected readonly control = new FormControl<TuiFileLike | null>(
    null,
    Validators.required,
  );

  protected readonly failedFiles$ = new Subject<TuiFileLike | null>();
  protected readonly loadingFiles$ = new Subject<TuiFileLike | null>();
  protected readonly loadedFiles$ = this.control.valueChanges.pipe(
    switchMap((file) => this.processFile(file)),
  );

  protected removeFile(): void {
    this.control.setValue(null);
  }

  protected processFile(file: TuiFileLike | null): Observable<TuiFileLike | null> {
    this.failedFiles$.next(null);

    if (this.control.invalid || !file) {
      return of(null);
    }

    this.loadingFiles$.next(file);

    return timer(1000).pipe(
      map(() => {
        if (Math.random() > 0.5) {
          return file;
        }

        this.failedFiles$.next(file);

        return null;
      }),
      finalize(() => this.loadingFiles$.next(null)),
    );
  }
}
