import { TestBed } from '@angular/core/testing';

import { CompradorResolverGuard } from './comprador-resolver.guard';

describe('CompradorResolverGuard', () => {
  let guard: CompradorResolverGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(CompradorResolverGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
