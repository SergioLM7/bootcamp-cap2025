import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FILMS_COMPONENTS } from './films.component';
import { PaginatorModule } from 'primeng/paginator';



@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    FILMS_COMPONENTS,
    PaginatorModule    
  ]
})
export class FilmsModule { }
