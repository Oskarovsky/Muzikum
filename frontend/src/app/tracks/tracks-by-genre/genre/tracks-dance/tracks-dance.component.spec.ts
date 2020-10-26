import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TracksDanceComponent } from './tracks-dance.component';

describe('TracksDanceComponent', () => {
  let component: TracksDanceComponent;
  let fixture: ComponentFixture<TracksDanceComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TracksDanceComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TracksDanceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
