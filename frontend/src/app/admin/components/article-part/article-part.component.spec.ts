import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ArticlePartComponent } from './article-part.component';

describe('ArticlePartComponent', () => {
  let component: ArticlePartComponent;
  let fixture: ComponentFixture<ArticlePartComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ArticlePartComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ArticlePartComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
