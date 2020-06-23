import { TestBed } from '@angular/core/testing';

import { FaturaResolveGuard } from './fatura-resolve.guard';

describe('FaturaResolveGuard', () => {
  let guard: FaturaResolveGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(FaturaResolveGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
