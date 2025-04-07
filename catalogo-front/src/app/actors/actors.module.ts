import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ACTORS_COMPONENTS } from './actors.component';
import { PaginatorModule } from 'primeng/paginator';



@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    ACTORS_COMPONENTS,
    PaginatorModule
  ]
})
export class ActorsModule { }