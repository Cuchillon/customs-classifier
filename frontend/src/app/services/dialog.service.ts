import { inject, Injectable, Injector, INJECTOR } from '@angular/core';
import { TuiDialogService } from '@taiga-ui/core';
import { PolymorpheusComponent } from '@taiga-ui/polymorpheus';
import { Subscription } from 'rxjs';
import { FullscreenLoader } from '../components/shared/fullscreen-loader/fullscreen-loader';
import { LOADER_TEXT } from '../tokens/loader-text.token';

export type DialogData = {
  label: string;
  content: string;
};

const SUCCESS_ICON = '\u2705';
const ERROR_ICON = '\u274C';

@Injectable({
  providedIn: 'root',
})
export class DialogService {
  private readonly parentInjector = inject(INJECTOR);
  private readonly dialogs = inject(TuiDialogService);
  private fullscreenLoaderRef: Subscription | null = null;

  public showSuccess(data: DialogData): void {
    const label = `${data.label} ${SUCCESS_ICON}`;
    this.showDialog(data.content, label);
  }

  public showError(data: DialogData): void {
    const label = `${data.label} ${ERROR_ICON}`;
    this.showDialog(data.content, label);
  }

  public showFullscreenLoader(text: string = 'Загрузка...') {
    if (this.fullscreenLoaderRef) return;

    const injector = Injector.create({
      providers: [
        { provide: LOADER_TEXT, useValue: text }
      ],
      parent: this.parentInjector
    });

    const content = new PolymorpheusComponent(FullscreenLoader, injector);

    this.fullscreenLoaderRef = this.dialogs
      .open(content, {
        label: '',
        size: 'fullscreen',
        closeable: false,
        dismissible: false
      })
      .subscribe();
  }

  public hideFullscreenLoader() {
    if (this.fullscreenLoaderRef) {
      this.fullscreenLoaderRef.unsubscribe();
      this.fullscreenLoaderRef = null;
    }
  }

  private showDialog(content: string, label: string): void {
    this.dialogs
      .open(content, {
        label: label,
        size: 's'
      }).subscribe();
  }
}
