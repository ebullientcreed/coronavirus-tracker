import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ChartViewConfirmedComponent } from './chart-view-confirmed.component';

describe('ChartViewConfirmedComponent', () => {
  let component: ChartViewConfirmedComponent;
  let fixture: ComponentFixture<ChartViewConfirmedComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ChartViewConfirmedComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ChartViewConfirmedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
