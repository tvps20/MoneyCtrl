import { TestBed } from '@angular/core/testing';

import { BandeiraService } from './bandeira.service';

describe('BandeiraService', () => {
  let service: BandeiraService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BandeiraService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
