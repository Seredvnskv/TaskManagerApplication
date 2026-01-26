export interface ExportTaskDTO {
  id: string;
  title: string;
  description: string;
  status: string;
  createdAt: string;
  updatedAt: string;
  dueDate: string;
  createdById: string;
  createdByUsername: string;
  assignedUserIds: string[];
  assignedUsernames: string[];
}
