import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { NotebooksComponent } from './notebooks/notebooks.component';
import { NotebookDetailComponent } from './notebook-detail/notebook-detail.component';

const routes: Routes = [
    { path: 'notebooks', component: NotebooksComponent },
    {
        path: 'notebooks/:id',
        canActivate: [],
        component: NotebookDetailComponent
    }
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class NotebookRoutingModule { }