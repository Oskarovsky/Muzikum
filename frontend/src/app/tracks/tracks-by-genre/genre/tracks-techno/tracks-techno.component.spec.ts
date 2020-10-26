import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TracksTechnoComponent } from './tracks-techno.component';

describe('TracksTechnoComponent', () => {
  let component: TracksTechnoComponent;
  let fixture: ComponentFixture<TracksTechnoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TracksTechnoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TracksTechnoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
