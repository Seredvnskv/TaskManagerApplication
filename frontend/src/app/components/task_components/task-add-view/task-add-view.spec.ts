import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TaskAddView } from './task-add-view';

describe('TaskAddView', () => {
  let component: TaskAddView;
  let fixture: ComponentFixture<TaskAddView>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TaskAddView]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TaskAddView);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
