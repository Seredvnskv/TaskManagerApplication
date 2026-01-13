import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {Header} from './ui/component/header/header';
import {Main} from './ui/component/main/main';
import {Footer} from './ui/component/footer/footer';
import {TaskListView} from './components/task_components/task-list-view/task-list-view';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('frontend');
}
