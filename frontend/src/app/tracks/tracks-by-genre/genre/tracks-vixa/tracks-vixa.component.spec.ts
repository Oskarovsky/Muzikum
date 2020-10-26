import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TracksVixaComponent } from './tracks-vixa.component';

describe('TracksVixaComponent', () => {
  let component: TracksVixaComponent;
  let fixture: ComponentFixture<TracksVixaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TracksVixaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TracksVixaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
