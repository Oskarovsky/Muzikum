import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UserPartComponent } from './user-part.component';

describe('UserPartComponent', () => {
  let component: UserPartComponent;
  let fixture: ComponentFixture<UserPartComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UserPartComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UserPartComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
