import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TracksDiscoComponent } from './tracks-disco.component';

describe('TracksDiscoComponent', () => {
  let component: TracksDiscoComponent;
  let fixture: ComponentFixture<TracksDiscoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TracksDiscoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TracksDiscoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
