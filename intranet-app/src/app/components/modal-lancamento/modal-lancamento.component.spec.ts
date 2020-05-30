import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ModalLancamentoComponent } from './modal-lancamento.component';

describe('ModalLancamentoComponent', () => {
  let component: ModalLancamentoComponent;
  let fixture: ComponentFixture<ModalLancamentoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ModalLancamentoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ModalLancamentoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
