import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ButtonBlock } from './button-block';

describe('ButtonBlock', () => {
  let component: ButtonBlock;
  let fixture: ComponentFixture<ButtonBlock>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ButtonBlock]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ButtonBlock);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
