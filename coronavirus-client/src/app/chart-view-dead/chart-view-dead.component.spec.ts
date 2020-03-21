import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ChartViewDeadComponent } from './chart-view-dead.component';

describe('ChartViewDeadComponent', () => {
  let component: ChartViewDeadComponent;
  let fixture: ComponentFixture<ChartViewDeadComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ChartViewDeadComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ChartViewDeadComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
