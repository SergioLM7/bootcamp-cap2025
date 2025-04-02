import { Routes } from '@angular/router';
import { HomeComponent } from './main';
import { DemosComponent } from './demos/demos.component';
import { FormulariosComponent } from './ejemplos/formularios/formularios.component';
import { ContactosAddComponent, ContactosEditComponent, ContactosListComponent, ContactosViewComponent } from './contactos';
import { PageNotFoundComponent } from './main/page-not-found/page-not-found.component';

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
    { path: 'contactos', children: [
        { path: '', component: ContactosListComponent},
        { path: 'add', component: ContactosAddComponent},
        { path: ':id/edit', component: ContactosEditComponent},
        { path: ':id', component: ContactosViewComponent},
        { path: ':id/:kk', component: ContactosViewComponent},
    ]},
    {
        path:'**',
        component: PageNotFoundComponent,
    } 
];
