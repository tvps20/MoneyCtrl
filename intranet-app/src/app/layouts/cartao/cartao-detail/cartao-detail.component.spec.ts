import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CartaoDetailComponent } from './cartao-detail.component';

describe('CartaoDetailComponent', () => {
  let component: CartaoDetailComponent;
  let fixture: ComponentFixture<CartaoDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CartaoDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CartaoDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
