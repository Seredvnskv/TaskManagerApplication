import { Component, OnInit } from '@angular/core';
import {TaskService} from '../../../services/task/task_service/task-service';
import {Task} from '../../../models/task/task';
import { ChangeDetectorRef } from '@angular/core';
import {TaskStatus} from '../../../models/enums/task_status/task-status';
import { CommonModule } from '@angular/common';
import {RouterLink} from '@angular/router';
import {FormsModule} from '@angular/forms';
import {User} from '../../../models/user/user';
import {UserService} from '../../../services/user/user_service/user-service';

@Component({
  selector: 'app-task-list-view',
  imports: [CommonModule, RouterLink, FormsModule],
  templateUrl: './task-list-view.html',
  styleUrl: './task-list-view.css',
})
export class TaskListView implements OnInit {
  constructor(private taskService: TaskService, private userService: UserService, private cdr: ChangeDetectorRef) {}
  protected readonly TaskStatus = TaskStatus;

  tasks: Task[] | undefined;
  users: User[] | undefined;

  filters = {
    searchText: '',
    showToDo: true,
    showInProgress: true,
    showDone: true,
    user: '',
    dateFrom: '',
    dateTo: ''
  }

  ngOnInit(): void {
    this.getUsers();
    this.getTasks();
  }

  private getUsers() {
    this.userService.getUsers().subscribe(
      users => {
        this.users = users;
        this.cdr.detectChanges();
      }
    );
  }

  private getTasks() {
    this.taskService.getTasks()
      .subscribe(tasks => {
        this.tasks = this.filterTasks(tasks);
        this.cdr.detectChanges();
      });
  }

  protected exportTasks() {
    this.taskService.exportTasks().subscribe(tasks => {
      const csv = this.convertToCSV(tasks);
      const blob = new Blob([csv], { type: 'text/csv;charset=utf-8;' });
      const link = document.createElement('a');
      const url = URL.createObjectURL(blob);

      link.setAttribute('href', url);
      link.setAttribute('download', `tasks_export_${new Date().toISOString().split('T')[0]}.csv`);
      link.style.visibility = 'hidden';
      document.body.appendChild(link);
      link.click();
      document.body.removeChild(link);
    });
  }

  private convertToCSV(tasks: any[]): string {
    if (tasks.length === 0) return '';

    const headers = Object.keys(tasks[0]);
    const csvHeaders = headers.join(',');

    const csvRows = tasks.map(task => {
      return headers.map(header => {
        const value = task[header];
        if (Array.isArray(value)) {
          return `"[${value.join(', ')}]"`;
        }

        return value;
      }).join(',');
    }).join('\n');

    return `${csvHeaders}\n${csvRows}`;
  }

  protected getTasksByStatus(status: TaskStatus): Task[] {
    if (!this.tasks) return [];
    return this.tasks.filter(task => task.status === status);
  }

  private filterTasks(tasks: Task[]): Task[] {
    if (!tasks) return [];

    return tasks.filter(task => {
      const searchTextMatch = this.filters.searchText === '' ||
        task.title.toLowerCase().includes(this.filters.searchText.toLowerCase()) ||
        task.description.toLowerCase().includes(this.filters.searchText.toLowerCase());

      const statusMatch =
        (this.filters.showToDo && task.status === TaskStatus.TODO) ||
        (this.filters.showInProgress && task.status === TaskStatus.IN_PROGRESS) ||
        (this.filters.showDone && task.status === TaskStatus.DONE);

      const userMatch = this.filters.user === '' || this.filters.user === task.createdBy;
      const dateFromMatch = this.filters.dateFrom === '' || new Date(task.dueDate) >= new Date(this.filters.dateFrom);
      const dateToMatch = this.filters.dateTo === '' || new Date(task.dueDate) <= new Date(this.filters.dateTo);

      return searchTextMatch && statusMatch && userMatch && dateFromMatch && dateToMatch;
    });
  }

  protected applyFilters() {
    this.getTasks();
  }

  protected resetFilters() {
    this.filters = {
      searchText: '',
      showToDo: true,
      showInProgress: true,
      showDone: true,
      user: '',
      dateFrom: '',
      dateTo: ''
    };

    this.getTasks();
  }
}
