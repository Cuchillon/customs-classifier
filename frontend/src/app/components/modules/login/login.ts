import { ChangeDetectionStrategy, Component } from '@angular/core';
import { TuiAppearance } from "@taiga-ui/core";
import { TuiCardLarge } from "@taiga-ui/layout";

@Component({
  selector: 'app-login',
    imports: [
        TuiAppearance,
        TuiCardLarge
    ],
  templateUrl: './login.html',
  styleUrl: './login.less',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class Login {

}
