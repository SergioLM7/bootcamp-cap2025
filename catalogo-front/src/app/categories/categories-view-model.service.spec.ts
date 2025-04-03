import { TestBed } from '@angular/core/testing';

import { CategoriesViewModelService } from './categories-view-model.service';

describe('CategoriesViewModelService', () => {
  let service: CategoriesViewModelService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CategoriesViewModelService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
