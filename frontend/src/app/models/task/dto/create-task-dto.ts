export interface CreateTaskDTO {
  createdByUserId: string;
  title: string;
  description: string;
  status: string;
  dueDate: string;
  assignedUsers: string[];
}
