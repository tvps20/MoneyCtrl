import { TestBed } from '@angular/core/testing';

import { CompradorService } from './comprador.service';

describe('CompradorService', () => {
  let service: CompradorService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CompradorService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
