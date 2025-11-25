import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SimilaritySearch } from './similarity-search';

describe('SimilaritySearch', () => {
  let component: SimilaritySearch;
  let fixture: ComponentFixture<SimilaritySearch>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SimilaritySearch]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SimilaritySearch);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
