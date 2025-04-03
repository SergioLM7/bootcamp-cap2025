import { TestBed } from '@angular/core/testing';

import { CategoriesViewModelService } from './categoriesViewModel.service';

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
