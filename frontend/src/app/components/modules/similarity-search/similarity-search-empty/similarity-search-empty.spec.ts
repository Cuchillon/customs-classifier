import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SimilaritySearchEmpty } from './similarity-search-empty';

describe('SimilaritySearchEmpty', () => {
  let component: SimilaritySearchEmpty;
  let fixture: ComponentFixture<SimilaritySearchEmpty>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SimilaritySearchEmpty]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SimilaritySearchEmpty);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
