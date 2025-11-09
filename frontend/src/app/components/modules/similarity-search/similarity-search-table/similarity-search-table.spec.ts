import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SimilaritySearchTable } from './similarity-search-table';

describe('SimilaritySearchTable', () => {
  let component: SimilaritySearchTable;
  let fixture: ComponentFixture<SimilaritySearchTable>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SimilaritySearchTable]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SimilaritySearchTable);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
