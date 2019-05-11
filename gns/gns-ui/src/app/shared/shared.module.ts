import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { ToastrModule } from 'ngx-toastr';
import {
    MatTableModule,
    MatPaginatorModule,
    MatInputModule,
    MatSortModule
} from '@angular/material';

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
        MatPaginatorModule,
        MatInputModule,
        MatSortModule,
        ToastrModule.forRoot(
            {
                timeOut: 3000,
                positionClass: 'toast-bottom-right',
                preventDuplicates: true,
            }
        )
    ],
    exports: [
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        NgbModule,
        MatTableModule,
        MatPaginatorModule,
        MatInputModule,
        MatSortModule,
        ToastrModule,
        ShrinkNotesPipe,
        GNSDataTableComponent
    ]
})
export class SharedModule { }
