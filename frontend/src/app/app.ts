import { TuiRoot } from "@taiga-ui/core";
import { ChangeDetectionStrategy, Component, signal } from '@angular/core';
import { NavigationTabs } from './components/modules/navigation-tabs/navigation-tabs';

@Component({
  selector: 'app-root',
  imports: [TuiRoot, NavigationTabs],
  templateUrl: './app.html',
  styleUrl: './app.less',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class App {
  protected readonly title = signal('База знаний классификации товаров');
}
