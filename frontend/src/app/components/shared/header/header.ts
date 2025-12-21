import { ChangeDetectionStrategy, Component, inject, input } from '@angular/core';
import { TuiButton } from '@taiga-ui/core';
import { AuthService } from '../../../services/auth.service';

@Component({
  selector: 'app-header',
  imports: [
    TuiButton
  ],
  templateUrl: './header.html',
  styleUrl: './header.less',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class Header {
  protected readonly authService = inject(AuthService);
  public readonly title = input.required<string>();
}
