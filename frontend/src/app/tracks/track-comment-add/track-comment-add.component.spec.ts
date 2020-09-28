import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TrackCommentAddComponent } from './track-comment-add.component';

describe('TrackCommentAddComponent', () => {
  let component: TrackCommentAddComponent;
  let fixture: ComponentFixture<TrackCommentAddComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TrackCommentAddComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TrackCommentAddComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
