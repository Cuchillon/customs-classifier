import { ChangeDetectionStrategy, Component, inject } from '@angular/core';
import {
  TuiAppearance,
  TuiButton,
  TuiError,
  TuiIcon,
  TuiLabel,
  TuiTextfield,
  TuiTextfieldComponent
} from "@taiga-ui/core";
import { TuiCardLarge } from "@taiga-ui/layout";
import { AsyncPipe } from '@angular/common';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { TuiFieldErrorPipe, TuiPassword } from '@taiga-ui/kit';
import { AuthService } from '../../../services/auth.service';

@Component({
  selector: 'app-login',
  imports: [
    TuiAppearance,
    TuiCardLarge,
    AsyncPipe,
    ReactiveFormsModule,
    TuiError,
    TuiFieldErrorPipe,
    TuiLabel,
    TuiTextfieldComponent,
    TuiTextfield,
    TuiIcon,
    TuiPassword,
    TuiButton
  ],
  templateUrl: './login.html',
  styleUrl: './login.less',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class Login {
  private authService = inject(AuthService);

  protected readonly formGroup = new FormGroup({
    login: new FormControl<string>('', { validators: Validators.required, nonNullable: true }),
    password: new FormControl<string>('', { validators: Validators.required, nonNullable: true })
  });

  protected login() {
    if (this.formGroup.valid) {
      const username = this.formGroup.controls.login.value;
      const password = this.formGroup.controls.password.value;
      this.authService.login(username, password);
    }
  }
}
