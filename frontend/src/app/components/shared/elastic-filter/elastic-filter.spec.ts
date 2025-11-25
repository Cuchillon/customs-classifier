import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ElasticFilter } from './elastic-filter';

describe('ElasticFilter', () => {
  let component: ElasticFilter;
  let fixture: ComponentFixture<ElasticFilter>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ElasticFilter]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ElasticFilter);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
