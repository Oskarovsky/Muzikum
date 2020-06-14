import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TrackPartComponent } from './track-part.component';

describe('TrackPartComponent', () => {
  let component: TrackPartComponent;
  let fixture: ComponentFixture<TrackPartComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TrackPartComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TrackPartComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
