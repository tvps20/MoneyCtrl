import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CompradorDetailComponent } from './comprador-detail.component';

describe('CompradorDetailComponent', () => {
  let component: CompradorDetailComponent;
  let fixture: ComponentFixture<CompradorDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CompradorDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CompradorDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
