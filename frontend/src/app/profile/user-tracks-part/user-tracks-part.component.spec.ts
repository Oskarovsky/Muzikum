import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UserTracksPartComponent } from './user-tracks-part.component';

describe('UserTracksPartComponent', () => {
  let component: UserTracksPartComponent;
  let fixture: ComponentFixture<UserTracksPartComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UserTracksPartComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UserTracksPartComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
