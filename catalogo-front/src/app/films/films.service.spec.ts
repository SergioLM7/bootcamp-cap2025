import { TestBed } from '@angular/core/testing';

import { FilmsViewModelService } from './filmsViewModel.service';

describe('FilmsService', () => {
  let service: FilmsViewModelService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FilmsViewModelService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
