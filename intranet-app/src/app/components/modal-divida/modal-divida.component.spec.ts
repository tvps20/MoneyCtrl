import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ModalDividaComponent } from './modal-divida.component';

describe('ModalDividaComponent', () => {
  let component: ModalDividaComponent;
  let fixture: ComponentFixture<ModalDividaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ModalDividaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ModalDividaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
