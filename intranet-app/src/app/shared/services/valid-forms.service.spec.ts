import { TestBed } from '@angular/core/testing';

import { ValidFormsService } from './valid-forms.service';

describe('ValidFormsService', () => {
  let service: ValidFormsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ValidFormsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
