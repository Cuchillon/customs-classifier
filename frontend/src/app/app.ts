import { TuiRoot } from "@taiga-ui/core";
import { ChangeDetectionStrategy, Component, inject, signal } from '@angular/core';
import { NavigationTabs } from './components/modules/navigation-tabs/navigation-tabs';
import { AuthService } from './services/auth.service';
import { Login } from './components/modules/login/login';

@Component({
  selector: 'app-root',
  imports: [TuiRoot, NavigationTabs, Login],
  templateUrl: './app.html',
  styleUrl: './app.less',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class App {
  protected readonly authService = inject(AuthService);
  protected readonly title = signal<string>('База знаний классификации товаров');
}
