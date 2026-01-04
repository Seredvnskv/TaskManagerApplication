import {RouterModule, Routes} from '@angular/router';
import {TaskListView} from './components/task_components/task-list-view/task-list-view';
import {NgModule} from '@angular/core';

export const routes: Routes = [
  {
    path: "tasks",
    component: TaskListView
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

