import { TestBed } from '@angular/core/testing';

import { VerificaUniqueFieldService } from './verifica-unique-field.service';

describe('VerificaUniqueFieldService', () => {
  let service: VerificaUniqueFieldService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(VerificaUniqueFieldService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
