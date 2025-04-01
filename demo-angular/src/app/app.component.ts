import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
// import { LoggerService } from '@my/core';
//import { DemosComponent } from './demos/demos.component';
import { HomeComponent, NotificationModalComponent } from './main';
import { FormulariosComponent } from './ejemplos/formularios/formularios.component';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, NotificationModalComponent, CommonModule, FormulariosComponent, HomeComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  // constructor(out: LoggerService) {
  //   out.error("Es un error");
  //   out.warn("Es un warn");
  //   out.info("Es un info");
  //   out.log("Es un log");
  // }
}
 