import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';

import { ShrinkNotesPipe } from './pipes/shrink-notes.pipe';
import { GNSDataTableComponent } from './components/datatable/datatable.component';

@NgModule({
  declarations: [
    ShrinkNotesPipe,
    GNSDataTableComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    NgbModule,
    MatTableModule,
    MatPaginatorModule
  ],
  exports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    NgbModule,
    MatTableModule,
    MatPaginatorModule,
    ShrinkNotesPipe,
    GNSDataTableComponent
  ]
})
export class SharedModule { }
