import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FileInputField } from './file-input-field';

describe('FileInputField', () => {
  let component: FileInputField;
  let fixture: ComponentFixture<FileInputField>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FileInputField]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FileInputField);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
