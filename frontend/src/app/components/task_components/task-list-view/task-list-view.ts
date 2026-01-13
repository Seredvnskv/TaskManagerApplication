import { Component, OnInit } from '@angular/core';
import {TaskService} from '../../../services/task/task_service/task-service';
import {Task} from '../../../models/task/task';
import { ChangeDetectorRef } from '@angular/core';
import {TaskStatus} from '../../../models/enums/task_status/task-status';
import { CommonModule } from '@angular/common';
import {RouterLink} from '@angular/router';

@Component({
  selector: 'app-task-list-view',
  imports: [CommonModule, RouterLink],
  templateUrl: './task-list-view.html',
  styleUrl: './task-list-view.css',
})
export class TaskListView implements OnInit {
  constructor(private taskService: TaskService,  private cdr: ChangeDetectorRef) {}

  tasks: Task[] | undefined;

  ngOnInit(): void {
    this.taskService.getTasks()
      .subscribe(tasks => {
        this.tasks = tasks;
        this.cdr.detectChanges();
      });
  }

  getTasksByStatus(status: TaskStatus): Task[] {
    if (!this.tasks) return [];
    return this.tasks.filter(task => task.status === status);
  }

  protected readonly TaskStatus = TaskStatus;
}
