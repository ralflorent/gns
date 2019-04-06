import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { ToastrModule } from 'ngx-toastr';

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
        ToastrModule,
        ShrinkNotesPipe,
        GNSDataTableComponent
    ]
})
export class SharedModule { }
