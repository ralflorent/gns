import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NotebookFormComponent } from './notebook-form.component';

describe('NotebookFormComponent', () => {
  let component: NotebookFormComponent;
  let fixture: ComponentFixture<NotebookFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NotebookFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NotebookFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
