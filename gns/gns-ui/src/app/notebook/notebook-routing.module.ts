import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { NotebooksComponent } from './notebooks/notebooks.component';
import { NotebookDetailComponent } from './notebook-detail/notebook-detail.component';
import { NotebookFormComponent } from './notebook-form/notebook-form.component';

const routes: Routes = [
    { 
        path: 'notebooks', 
        canActivate: [],
        children: [
            {
                path: '',
                component: NotebooksComponent
            },
            {
                path: ':id',
                canActivate: [],
                component: NotebookDetailComponent
            },
            {
                path: ':id/edit',
                canActivate: [],
                component: NotebookFormComponent
            }
        ] 
    }
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class NotebookRoutingModule { }