import { Routes } from '@angular/router';
import { HomeComponent } from './main';
import { DemosComponent } from './demos/demos.component';
import { FormulariosComponent } from './ejemplos/formularios/formularios.component';

export const routes: Routes = [
    {
        path:'',
        component: HomeComponent,
    },
    {
        path:'inicio',
        component: HomeComponent,
    },
    {
        path:'demos',
        component: DemosComponent, title:'Demostración',
    },
    {
        path:'esto/es/un/formulario',
        component: FormulariosComponent,
    },
    {
        path:'personas',
        component:FormulariosComponent,
    },
    {
        path:'personas/add',
        component:FormulariosComponent,
    },
    {
        path:'personas/:id/edit',
        component:FormulariosComponent,
    },
    {
        path:'personas/:id',
        component:FormulariosComponent,
    },

];
