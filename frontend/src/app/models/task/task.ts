import {TaskStatus} from '../enums/task_status/task-status';
import {User} from '../user/user';

export interface Task {
  id: string;
  title: string;
  description: string;
  status: TaskStatus;
  createdBy: User
  createdAt: Date;
  updatedAt: Date;
  dueDate: Date;
}
