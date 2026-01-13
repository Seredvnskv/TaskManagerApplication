import { Injectable } from '@angular/core';
import {TaskMapper} from '../task_mapper/task-mapper';
import {HttpClient} from '@angular/common/http';
import {map, Observable} from 'rxjs';
import {Task} from '../../../models/task/task';
import {ReadTaskDTO} from '../../../models/task/dto/read-task-dto';

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
}
