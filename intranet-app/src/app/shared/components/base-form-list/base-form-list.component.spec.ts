import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BaseFormListComponent } from './base-form-list.component';

describe('BaseFormListComponent', () => {
  let component: BaseFormListComponent;
  let fixture: ComponentFixture<BaseFormListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BaseFormListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BaseFormListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
