import { ChangeDetectionStrategy, Component, inject, Injector, signal } from '@angular/core';
import { TuiLoader } from '@taiga-ui/core';
import { LOADER_TEXT } from '../../../tokens/loader-text.token';

@Component({
  selector: 'app-fullscreen-loader',
  imports: [
    TuiLoader
  ],
  templateUrl: './fullscreen-loader.html',
  styleUrl: './fullscreen-loader.less',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class FullscreenLoader {
  private readonly injector = inject(Injector);
  protected readonly text = signal(this.injector.get(LOADER_TEXT));
}
