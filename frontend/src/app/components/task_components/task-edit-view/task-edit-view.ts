import {ChangeDetectorRef, Component, OnInit, ViewChild} from '@angular/core';
import {FormsModule, NgForm} from '@angular/forms';
import {TaskService} from '../../../services/task/task_service/task-service';
import {Task} from '../../../models/task/task';
import {ActivatedRoute, Router} from '@angular/router';
import {UpdateTaskDTO} from '../../../models/task/dto/update-task-dto';
import {TaskStatus} from '../../../models/enums/task_status/task-status';

@Component({
  selector: 'app-task-edit-view',
  imports: [
    FormsModule
  ],
  templateUrl: './task-edit-view.html',
  styleUrl: './task-edit-view.css',
})
export class TaskEditView implements OnInit {
  constructor(private taskService: TaskService,  private route: ActivatedRoute, private router: Router, private cdr: ChangeDetectorRef) {}

  @ViewChild('taskForm') taskForm!: NgForm;

  task: Task | undefined;

  ngOnInit() {
    this.loadTask();
  }

  private loadTask(): void {
    this.route.params.subscribe(params => {
      const id = params['id'];

      if (!id) {
        console.error('No task with such ID');
        return;
      }

      this.taskService.getTaskById(id).subscribe({
        next: (task) => {
          this.task = task;
          this.cdr.detectChanges();
        },
        error: (error) => {
          console.error('Error loading task:', error);
        }
      });
    })
  }

  protected saveTask(): void {
    if (this.task && this.taskForm.form.valid) {
      const updatedTask: UpdateTaskDTO = {
        title: this.taskForm.value.title,
        description: this.taskForm.value.description,
        status: this.taskForm.value.status as TaskStatus,
        dueDate: new Date(this.taskForm.value.dueDate).toISOString(),
      }

      this.taskService.updateTask(this.task.id, updatedTask).subscribe({
        next: (task) => {
          this.router.navigate(['/tasks', task.id]);
        },
        error: (error) => {
          console.error('Error updating task:', error);
        }
      });
    }
  }

  protected cancelEdit(): void {
    if (this.task) {
      this.router.navigate(['/tasks', this.task.id]);
    }
  }
}
