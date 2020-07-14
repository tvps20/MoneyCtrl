import { TestBed } from '@angular/core/testing';

import { CartaoResolveGuard } from './cartao-resolve.guard';

describe('CartaoResolveGuard', () => {
  let guard: CartaoResolveGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(CartaoResolveGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
