import { NgModule } from '@angular/core';

import { NotebookRoutingModule } from './notebook-routing.module';
import { SharedModule } from '../shared/shared.module';
import { NotebooksComponent } from './notebooks/notebooks.component';
import { NotebookDetailComponent } from './notebook-detail/notebook-detail.component';
import { NotebookService } from './notebook.service';

@NgModule({
  declarations: [NotebooksComponent, NotebookDetailComponent],
  imports: [
    NotebookRoutingModule,
    SharedModule
  ],
  providers: [NotebookService]
})
export class NotebookModule { }
