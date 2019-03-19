import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { CompleteSequencePipe } from './pipes/complete-sequence.pipe';
import { ShrinkNotesPipe } from './pipes/shrink-notes.pipe';

@NgModule({
  declarations: [
    CompleteSequencePipe,
    ShrinkNotesPipe
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule
  ],
  exports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    CompleteSequencePipe,
    ShrinkNotesPipe
  ]
})
export class SharedModule { }
