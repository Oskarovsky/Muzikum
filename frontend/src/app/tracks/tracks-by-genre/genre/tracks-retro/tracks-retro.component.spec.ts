import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TracksRetroComponent } from './tracks-retro.component';

describe('TracksRetroComponent', () => {
  let component: TracksRetroComponent;
  let fixture: ComponentFixture<TracksRetroComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TracksRetroComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TracksRetroComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
