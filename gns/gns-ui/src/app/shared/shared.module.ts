import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ShrinkNotesPipe } from './pipes/shrink-notes.pipe';

@NgModule({
  declarations: [
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
    ShrinkNotesPipe
  ]
})
export class SharedModule { }
