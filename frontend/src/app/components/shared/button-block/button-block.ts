import { ChangeDetectionStrategy, Component, input, output } from '@angular/core';
import { TuiButton } from '@taiga-ui/core';

export type ButtonBlockTitles = {
  left: string;
  right: string;
};

@Component({
  selector: 'app-button-block',
  imports: [
    TuiButton
  ],
  templateUrl: './button-block.html',
  styleUrl: './button-block.less',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ButtonBlock {
  public titles = input.required<ButtonBlockTitles>();
  protected leftClicked = output<Event>();
  protected rightClicked = output<Event>();
}
