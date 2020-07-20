import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ModalCotasComponent } from './modal-cotas.component';

describe('ModalCotasComponent', () => {
  let component: ModalCotasComponent;
  let fixture: ComponentFixture<ModalCotasComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ModalCotasComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ModalCotasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
