import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TaskEditView } from './task-edit-view';

describe('TaskEditView', () => {
  let component: TaskEditView;
  let fixture: ComponentFixture<TaskEditView>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TaskEditView]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TaskEditView);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
