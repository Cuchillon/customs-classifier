import { ChangeDetectionStrategy, Component } from '@angular/core';
import { RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { TuiTabs } from '@taiga-ui/kit';

@Component({
  selector: 'app-navigation-tabs',
  imports: [RouterLink, RouterLinkActive, RouterOutlet, TuiTabs],
  templateUrl: './navigation-tabs.html',
  styleUrl: './navigation-tabs.less',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class NavigationTabs {
  protected readonly tabs = [
    {
      url: 'similarity-search',
      name: 'Выполнить поиск по сходимости'
    },
    {
      url: 'add-specification',
      name: 'Добавить спецификацию'
    }
  ];
}
