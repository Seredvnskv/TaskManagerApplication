import {ReadTaskDTO} from '../../../models/task/dto/read-task-dto';
import {Task} from '../../../models/task/task';
import {TaskStatus} from '../../../models/enums/task_status/task-status';
import {UserMapper} from '../../user/user_mapper/user-mapper';
import {Injectable} from '@angular/core';
import {UserService} from '../../user/user_service/user-service';

@Injectable({
  providedIn: 'root'
})
export class TaskMapper {
  constructor(private userMapper: UserMapper, private userService: UserService) {}

  toEntity(dto: ReadTaskDTO): Task {
    return {
      id: dto.id,
      title: dto.title,
      description: dto.description,
      status: dto.status as TaskStatus,
      createdBy: dto.createdBy,
      createdAt: new Date(dto.createdAt),
      updatedAt: new Date(dto.updatedAt),
      dueDate: new Date(dto.dueDate),
      assignedUsers: dto.assignedUsers
    };
  }

  toDto(entity: Task): ReadTaskDTO {
    return {
      id: entity.id,
      title: entity.title,
      description: entity.description,
      status: entity.status,
      createdBy: entity.createdBy,
      createdAt: entity.createdAt.toISOString(),
      updatedAt: entity.updatedAt.toISOString(),
      dueDate: entity.dueDate.toISOString(),
      assignedUsers: entity.assignedUsers
    };
  }

  collectionDtoToEntity(dtos: ReadTaskDTO[]): Task[] {
    return dtos.map(dto => this.toEntity(dto));
  }
}
