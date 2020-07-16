import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DividaComponent } from './divida.component';

describe('DividaComponent', () => {
  let component: DividaComponent;
  let fixture: ComponentFixture<DividaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DividaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DividaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
