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

  ngOnInit() {
    this.loadTaskDetails();
    this.cdr.detectChanges();
  }

  private loadTaskDetails() {
    this.route.params.subscribe(params => {
      const id = params['id'];

      this.taskService.getTaskById(id).subscribe(task => {
        this.task = task;
      });
    })
  }
}
