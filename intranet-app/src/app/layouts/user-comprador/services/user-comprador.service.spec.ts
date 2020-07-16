import { TestBed } from '@angular/core/testing';

import { UserCompradorService } from './user-comprador.service';

describe('UserCompradorService', () => {
  let service: UserCompradorService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UserCompradorService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
