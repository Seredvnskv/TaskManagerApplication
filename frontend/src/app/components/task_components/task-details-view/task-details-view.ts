import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {Task} from '../../../models/task/task';
import {TaskService} from '../../../services/task/task_service/task-service';
import {ActivatedRoute, Router} from '@angular/router';
import {DatePipe} from '@angular/common';

@Component({
  selector: 'app-task-details-view',
  imports: [
    DatePipe
  ],
  templateUrl: './task-details-view.html',
  styleUrl: './task-details-view.css',
})
export class TaskDetailsView implements OnInit {
  constructor (private taskService: TaskService, private route: ActivatedRoute, private router: Router, private cdr: ChangeDetectorRef) {}

  task: Task | undefined;
  loading = false;
  error: string | null = null;

  ngOnInit() {
    this.loadTaskDetails();
  }

  private loadTaskDetails() {
    this.route.params.subscribe(params => {
      const id = params['id'];
      console.log('Task ID from route:', id);

      if (!id) {
        this.error = 'No task ID provided';
        return;
      }

      this.loading = true;
      this.error = null;

      this.taskService.getTaskById(id).subscribe({
        next: (task) => {
          console.log('Task received:', task);
          this.task = task;
          this.loading = false;
          this.cdr.detectChanges();
        },
        error: (error) => {
          console.error('Error loading task:', error);
          this.error = `Failed to load task: ${error.message || 'Unknown error'}`;
          this.loading = false;
          this.cdr.detectChanges();
        }
      });
    })
  }

  protected editTask() {
    if (this.task) {
      this.router.navigate(['/tasks', this.task.id, 'edit']);
    }
  }

  protected deleteTask(id: string) {
    this.taskService.deleteTask(id).subscribe(
      () => {
        console.log('Task deleted successfully');
        this.goBack();
      }
    );
  }

  protected goBack() {
    this.router.navigate(['/tasks']);
  }
}
