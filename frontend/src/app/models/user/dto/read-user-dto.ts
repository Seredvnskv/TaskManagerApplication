import {Role} from '../../enums/role/role';

export interface ReadUserDTO {
  id: string;
  username: string;
  email: string;
  createdAt: Date;
  role: string;
}
