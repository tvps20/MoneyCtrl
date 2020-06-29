import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NotificationStateComponent } from './notification-state.component';

describe('NotificationStateComponent', () => {
  let component: NotificationStateComponent;
  let fixture: ComponentFixture<NotificationStateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NotificationStateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NotificationStateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
