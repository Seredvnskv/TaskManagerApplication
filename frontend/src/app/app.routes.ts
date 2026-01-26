import {RouterModule, Routes} from '@angular/router';
import {TaskListView} from './components/task_components/task-list-view/task-list-view';
import {NgModule} from '@angular/core';
import {UserListView} from './components/user_components/user-list-view/user-list-view';
import {TaskDetailsView} from './components/task_components/task-details-view/task-details-view';
import {TaskEditView} from './components/task_components/task-edit-view/task-edit-view';
import {TaskAddView} from './components/task_components/task-add-view/task-add-view';

export const routes: Routes = [
  {
    path: "",
    redirectTo: "tasks",
    pathMatch: "full"
  },
  {
    path: "tasks",
    component: TaskListView
  },
  {
    path: "tasks/add",
    component: TaskAddView
  },
  {
    path: "tasks/export",
    component: TaskListView
  },
  {
    path: "tasks/:id",
    component: TaskDetailsView
  },
  {
    path: "tasks/:id/edit",
    component: TaskEditView
  },
  {
    path: "users",
    component: UserListView
  },
  {
    path: "users/username/:username",
    component: UserListView
  }
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes)
  ],
  exports: [
    RouterModule
  ]
})
export class AppRoutingModule {
}

