import { Routes } from '@angular/router';
import { HomeComponent } from './main/home/home.component';
import { FilmsAddComponent, FilmsEditComponent, FilmsListComponent, FilmsViewComponent } from './films';
import { PageNotFoundComponent } from './main/page-not-found/page-not-found.component';
import { LanguagesAddComponent, LanguagesEditComponent, LanguagesListComponent, LanguagesViewComponent } from './languages';
import { CategoriesAddComponent, CategoriesEditComponent, CategoriesListComponent, CategoriesViewComponent } from './categories';

export const routes: Routes = [
    {
        path:"",
        component: HomeComponent,
    },
    { path: 'films', 
        children: [
        { path: '', component: FilmsListComponent},
        { path: 'add', component: FilmsAddComponent},
        { path: ':id/edit', component: FilmsEditComponent},
        { path: ':id', component: FilmsViewComponent},
    ]},
    { path: 'languages', 
        children: [
        { path: '', component: LanguagesListComponent},
        { path: 'add', component: LanguagesAddComponent},
        { path: ':id/edit', component: LanguagesEditComponent},
        { path: ':id', component: LanguagesViewComponent},
    ]},
    { path: 'categories', 
        children: [
        { path: '', component: CategoriesListComponent},
        { path: 'add', component: CategoriesAddComponent},
        { path: ':id/edit', component: CategoriesEditComponent},
        { path: ':id', component: CategoriesViewComponent},
    ]},
    {
        path:'**',
        component: PageNotFoundComponent,
    } 
    
];
