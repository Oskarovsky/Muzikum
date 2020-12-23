import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ZippyPlayerComponent } from './zippy-player.component';

describe('ZippyPlayerComponent', () => {
  let component: ZippyPlayerComponent;
  let fixture: ComponentFixture<ZippyPlayerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ZippyPlayerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ZippyPlayerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
