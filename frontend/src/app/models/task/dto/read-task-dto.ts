import {ReadUserDTO} from '../../user/dto/read-user-dto';

export interface ReadTaskDTO {
  id: string;
  title: string;
  description: string;
  status: string;
  createdBy: ReadUserDTO;
  createdAt: Date;
  updatedAt: Date;
  dueDate: Date;
}
