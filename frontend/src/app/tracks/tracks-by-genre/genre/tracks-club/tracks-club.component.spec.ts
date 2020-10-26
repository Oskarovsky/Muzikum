import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TracksClubComponent } from './tracks-club.component';

describe('TracksClubComponent', () => {
  let component: TracksClubComponent;
  let fixture: ComponentFixture<TracksClubComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TracksClubComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TracksClubComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
