import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UserTrackTileComponent } from './user-track-tile.component';

describe('UserTrackTileComponent', () => {
  let component: UserTrackTileComponent;
  let fixture: ComponentFixture<UserTrackTileComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UserTrackTileComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UserTrackTileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
