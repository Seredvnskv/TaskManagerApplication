import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import {TaskService} from '../../../services/task/task_service/task-service';
import {User} from '../../../models/user/user';
import {UserService} from '../../../services/user/user_service/user-service';
import {Router, RouterLink} from '@angular/router';
import {CreateTaskDTO} from '../../../models/task/dto/create-task-dto';

@Component({
  selector: 'app-task-add-view',
  imports: [
    FormsModule,
    ReactiveFormsModule,
    RouterLink
  ],
  templateUrl: './task-add-view.html',
  styleUrl: './task-add-view.css',
})
export class TaskAddView implements OnInit {
  taskForm!: FormGroup
  users: User[] | undefined;

  constructor(private formBuilder: FormBuilder, private taskService: TaskService, private userService: UserService,
              private cdr: ChangeDetectorRef, private router: Router)
  {
    this.taskForm = this.formBuilder.group({
      title: ['', Validators.required],
      description: ['', Validators.required],
      status: [],
      dueDate: ['', Validators.required],
      assignedUsers: [[], Validators.required],
    });
  }

  ngOnInit() {
    this.getUsers();
  }

  private getUsers() {
    this.userService.getUsers().subscribe(
      users => {
        this.users = users;
        this.cdr.detectChanges();
      }
    );
  }

  get selectedUsers(): User[] {
    if (!this.users) return [];
    return this.users.filter(user => this.taskForm.get('assignedUsers')?.value.includes(user.id));
  }

  protected onUserToggle(id: string, event: any) {
    const selectedUsers = this.taskForm.get('assignedUsers')?.value as [];
    if (event.target.checked) {
      this.taskForm.patchValue({assignedUsers: [...selectedUsers, id]});
    }
    else {
      this.removeUser(id);
    }
  }

  protected isUserSelected(id: string): boolean {
    return this.taskForm.get('assignedUsers')?.value.includes(id);
  }

  protected removeUser(userId: string) {
    const assignedUsers = this.taskForm.get('assignedUsers')?.value as [];
    this.taskForm.patchValue({assignedUsers: assignedUsers.filter((id: string) => id !== userId)});
  }

  protected onSubmit() {
    if (!this.users) return;

    const task: CreateTaskDTO = {
      createdByUserId: this.users[Math.floor(Math.random() * (this.users.length))].id.toString(),
      title: this.taskForm.get('title')?.value,
      description: this.taskForm.get('description')?.value,
      status: 'TODO',
      dueDate: new Date(this.taskForm.get('dueDate')?.value).toISOString(),
      assignedUsers: this.taskForm.get('assignedUsers')?.value,
    }

    this.taskService.createTask(task).subscribe({
      next: () => {
        alert("Task created successfully!");
        this.resetForm();
        this.router.navigate(['/tasks']);
      }, error: () => {
        alert("Failed to create task. Please try again.");
      }, complete: () => {
        this.cdr.detectChanges();
      }
    });
  }

  protected resetForm() {
    this.taskForm.reset({
      title: '',
      description: '',
      status: null,
      dueDate: '',
      assignedUsers: []
    });
  }
}
