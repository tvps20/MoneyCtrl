import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FaturaDetailComponent } from './fatura-detail.component';

describe('FaturaDetailComponent', () => {
  let component: FaturaDetailComponent;
  let fixture: ComponentFixture<FaturaDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FaturaDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FaturaDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
