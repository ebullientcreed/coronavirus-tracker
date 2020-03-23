import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ChartViewRecoveredComponent } from './chart-view-recovered.component';

describe('ChartViewRecoveredComponent', () => {
  let component: ChartViewRecoveredComponent;
  let fixture: ComponentFixture<ChartViewRecoveredComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ChartViewRecoveredComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ChartViewRecoveredComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
