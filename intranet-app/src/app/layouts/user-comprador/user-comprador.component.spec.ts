import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UserCompradorComponent } from './user-comprador.component';

describe('UserCompradorComponent', () => {
  let component: UserCompradorComponent;
  let fixture: ComponentFixture<UserCompradorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UserCompradorComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UserCompradorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
