import { TestBed } from '@angular/core/testing';

import { ActorsViewModelService } from './actorsViewModel.service';

describe('ActorsViewModelService', () => {
  let service: ActorsViewModelService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ActorsViewModelService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
