import { Injectable } from '@angular/core';
import {TaskMapper} from '../task_mapper/task-mapper';
import {HttpClient} from '@angular/common/http';
import {map, Observable} from 'rxjs';
import {Task} from '../../../models/task/task';
import {ReadTaskDTO} from '../../../models/task/dto/read-task-dto';
import {UpdateTaskDTO} from '../../../models/task/dto/update-task-dto';
import {CreateTaskDTO} from '../../../models/task/dto/create-task-dto';
import {ExportTaskDTO} from '../../../models/task/dto/export-task-dto';

@Injectable({
  providedIn: 'root',
})
export class TaskService {
  constructor (private TaskMapper: TaskMapper, private http: HttpClient) {}

  getTasks(): Observable<Task[]> {
    return this.http.get<ReadTaskDTO[]>('api/tasks')
      .pipe(map(response => this.TaskMapper.collectionDtoToEntity(response)));
  }

  getTaskById(id: string) {
    return this.http.get<ReadTaskDTO>('api/tasks/' + id)
      .pipe(map(response => this.TaskMapper.toEntity(response)));
  }

  deleteTask(id: string): Observable<void> {
    return this.http.delete<void>('api/tasks/' + id);
  }

  updateTask(id: string, task: UpdateTaskDTO): Observable<Task> {
    return this.http.patch<ReadTaskDTO>('api/tasks/' + id, task)
      .pipe(map(response => this.TaskMapper.toEntity(response)));
  }

  createTask(task: CreateTaskDTO): Observable<Task> {
    return this.http.post<ReadTaskDTO>('api/tasks', task)
      .pipe(map(response => this.TaskMapper.toEntity(response)));
  }

  exportTasks(): Observable<ExportTaskDTO[]> {
    return this.http.get<ExportTaskDTO[]>('api/tasks/export');
  }
}
