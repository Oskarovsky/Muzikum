import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TracksSetComponent } from './tracks-set.component';

describe('TracksSetComponent', () => {
  let component: TracksSetComponent;
  let fixture: ComponentFixture<TracksSetComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TracksSetComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TracksSetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
