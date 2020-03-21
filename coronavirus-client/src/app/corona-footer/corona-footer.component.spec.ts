import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CoronaFooterComponent } from './corona-footer.component';

describe('CoronaFooterComponent', () => {
  let component: CoronaFooterComponent;
  let fixture: ComponentFixture<CoronaFooterComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CoronaFooterComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CoronaFooterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
