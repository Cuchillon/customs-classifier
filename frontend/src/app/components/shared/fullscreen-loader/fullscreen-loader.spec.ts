import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FullscreenLoader } from './fullscreen-loader';

describe('FullscreenLoader', () => {
  let component: FullscreenLoader;
  let fixture: ComponentFixture<FullscreenLoader>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FullscreenLoader]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FullscreenLoader);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
