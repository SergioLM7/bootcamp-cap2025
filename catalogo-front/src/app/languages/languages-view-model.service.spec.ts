import { TestBed } from '@angular/core/testing';

import { LanguagesViewModelService } from './languagesViewModel.service';

describe('LanguagesViewModelService', () => {
  let service: LanguagesViewModelService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LanguagesViewModelService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
