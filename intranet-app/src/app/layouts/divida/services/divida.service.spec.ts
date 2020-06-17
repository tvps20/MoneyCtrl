import { TestBed } from '@angular/core/testing';

import { DividaService } from './divida.service';

describe('DividaService', () => {
  let service: DividaService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DividaService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
