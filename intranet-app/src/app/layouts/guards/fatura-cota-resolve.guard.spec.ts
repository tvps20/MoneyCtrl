import { TestBed } from '@angular/core/testing';

import { FaturaCotaResolveGuard } from './fatura-cota-resolve.guard';

describe('FaturaCotaResolveGuard', () => {
  let guard: FaturaCotaResolveGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(FaturaCotaResolveGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
