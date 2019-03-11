import { Component, OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { AbstractControl, FormBuilder, FormGroup, Validators } from "@angular/forms";
import { debounceTime, distinctUntilChanged } from 'rxjs/operators';

import { NotebookService } from '../notebook.service';
import { VALIDATION_RULES } from '../../shared/constants/gns.constants';

@Component({
  selector: 'gns-notebook-form',
  templateUrl: './notebook-form.component.html',
  styleUrls: ['./notebook-form.component.scss']
})
export class NotebookFormComponent implements OnInit {

  form: FormGroup;
  loading: boolean;
  submitted: boolean;
  errorMsg: string;
  errorFields: object;
  private validators: any = VALIDATION_RULES.notebook

  constructor(private fb: FormBuilder, private service: NotebookService) { }

  ngOnInit() {
    this.loading      = false;
    this.submitted    = false;
    this.errorMsg     = '';
    this.errorFields  = {};

    this.form = this.fb.group({
      id: [0, ],
      noteId: ['AA00-00100'],
      latitude: [27.4580182],
      longitude: [-3.80182],
      date: [(new DatePipe('en-US').transform(new Date(), 'medium'))],
      description: ['', [
        this.validators.description.rules.REQUIRED ? Validators.required : null,
        Validators.minLength(this.validators.description.rules.MIN_LENGTH),
        Validators.maxLength(this.validators.description.rules.MAX_LENGTH),
      ]],
      note: ['', [this.validators.note.rules.REQUIRED ? Validators.required : null]],
    });

    // watch over future changes in form controls
    this.subscribeToValueChanges();
  }

  private subscribeToValueChanges() {
    Object.keys(this.form.value).map(key => {
      this.form.get(key)
        .valueChanges
        .pipe(debounceTime(1000), distinctUntilChanged())
        .subscribe(() => this.setValidationMessage(key));
    });
  }

  private setValidationMessage(controlName: string) {
    const control: AbstractControl = this.form.get(controlName);
    this.errorFields[controlName] = '';
    this.errorMsg = '';

    if ((control.touched || control.dirty) && control.errors)
      this.errorFields[controlName] = Object.keys(control.errors)
        .map(key => VALIDATION_RULES
          .notebook[controlName]
          .messages[key]
        )
        .join('. ');
  }

  submit(): void {
    if(!this.form.valid) return;
    this.loading = true;


  }

  resetForm(): void {
    this.submitted    = false;
    this.loading      = false;
    this.errorMsg     = '';
    this.errorFields  = {};
    this.form.reset();
  }

}
