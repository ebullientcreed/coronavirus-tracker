import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ChartViewAllComponent } from './chart-view-all.component';

describe('ChartViewAllComponent', () => {
  let component: ChartViewAllComponent;
  let fixture: ComponentFixture<ChartViewAllComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ChartViewAllComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ChartViewAllComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
