import { ChangeDetectionStrategy, Component, input } from '@angular/core';
import { TuiLoader, tuiLoaderOptionsProvider } from '@taiga-ui/core';

@Component({
  selector: 'app-loading-spinner',
  imports: [
    TuiLoader
  ],
  providers: [tuiLoaderOptionsProvider({size: 'xxl'})],
  templateUrl: './loading-spinner.html',
  styleUrl: './loading-spinner.less',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class LoadingSpinner {
  public readonly isLoading = input.required<boolean>();
}
