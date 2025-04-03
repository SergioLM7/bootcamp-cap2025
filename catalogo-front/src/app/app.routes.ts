import { Routes } from '@angular/router';
import { HomeComponent } from './main/home/home.component';
import { FilmsAddComponent, FilmsEditComponent, FilmsListComponent, FilmsViewComponent } from './films';
import { PageNotFoundComponent } from './main/page-not-found/page-not-found.component';

export const routes: Routes = [
    {
        path:"",
        component: FilmsListComponent,
    },
    { path: 'films', 
        children: [
        { path: '', component: FilmsListComponent},
        { path: 'add', component: FilmsAddComponent},
        { path: ':id/edit', component: FilmsEditComponent},
        { path: ':id', component: FilmsViewComponent},
    ]},
    {
        path:'**',
        component: PageNotFoundComponent,
    } 
    
];
