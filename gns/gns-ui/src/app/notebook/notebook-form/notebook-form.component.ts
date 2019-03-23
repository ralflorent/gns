import { Component, OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { Router, ActivatedRoute } from '@angular/router';
import { AbstractControl, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { debounceTime, distinctUntilChanged } from 'rxjs/operators';

import * as L from 'leaflet';

import { NotebookService } from '../notebook.service';
import { GNS_CONSTANTS, VALIDATION_RULES } from 'src/app/shared/constants/gns.constants';
import { detectBrowser } from 'src/app/shared/utils/detect-browser';
import { GPSLocation, Notebook } from 'src/app/shared/models';

@Component({
    selector: 'gns-notebook-form',
    templateUrl: './notebook-form.component.html',
    styleUrls: ['./notebook-form.component.scss']
})
export class NotebookFormComponent implements OnInit {

    form: FormGroup;
    formMode: 'add' | 'edit';
    browser: string;
    gnsmap: L.Map;
    loading: boolean;
    submitted: boolean;
    errorMsg: string;
    errorFields: { description?: string; note?: string };
    private validators: any = VALIDATION_RULES.notebook;

    constructor(
        private fb: FormBuilder,
        private service: NotebookService,
        private router: Router,
        private route: ActivatedRoute
    ) {
        this.browser = detectBrowser();
    }

    ngOnInit(): void {
        this.loading = false;
        this.submitted = false;
        this.errorMsg = '';
        this.errorFields = {};

        this.form = this.fb.group({
            id: 0,
            noteId: '',
            latitude: 0,
            longitude: 0,
            gnsDate: '',
            description: ['', [
                this.validators.description.rules.REQUIRED ? Validators.required : null,
                Validators.minLength(this.validators.description.rules.MIN_LENGTH),
                Validators.maxLength(this.validators.description.rules.MAX_LENGTH),
            ]],
            note: ['', [this.validators.note.rules.REQUIRED ? Validators.required : null]],
        });

        // watch over future changes in form controls
        this.subscribeToValueChanges();

        // init the geolocation for maps
        this.service.getLocation()
            .subscribe(
                (gps: GPSLocation) => {
                    this.form.patchValue({
                        ...gps,
                        gnsDate: (new DatePipe('en-US').transform(gps.gnsDate, 'medium'))
                    });
                    this.gnsmap = L.map('gns-map').setView([gps.latitude, gps.longitude], 13);
                    this.buildTileLayer([gps.latitude, gps.longitude]);
                },
                () => this.errorMsg = `No GPS location available now.`
            );

        const id = +this.route.snapshot.paramMap.get('id');
        if (id > 0) {
            this.formMode = 'edit';
            this.service.getOne(id)
                .subscribe(
                    (nb: Notebook) => this.form.patchValue({
                        id: nb.id,
                        description: nb.description,
                        note: nb.note
                    }),
                    (error: string) => console.log(error)
                );
        } else {
            this.formMode = 'add';
        }

    }

    private subscribeToValueChanges(): void {
        Object.keys(this.form.value).map(key => {
            this.form.get(key)
                .valueChanges
                .pipe(debounceTime(1000), distinctUntilChanged())
                .subscribe(() => this.setValidationMessage(key));
        });
    }

    private setValidationMessage(controlName: string): void {
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

    private buildTileLayer(
        location: [number, number]
        , option: 'STATIC' | 'API' | 'ONLINE' = 'STATIC'): void {
        let url = GNS_CONSTANTS.leaflet.STATIC_URL;

        switch (option) {
            case 'STATIC':
                url = GNS_CONSTANTS.leaflet.STATIC_URL;
                break;
            case 'API':
                url = GNS_CONSTANTS.leaflet.API_URL;
                break;
            case 'ONLINE':
                url = GNS_CONSTANTS.leaflet.ONLINE_URL;
                break;
        }

        L.tileLayer(url, {
            attribution: GNS_CONSTANTS.leaflet.ATTRIBUTION,
            maxZoom: 18,
            id: 'mapbox.streets',
            accessToken: GNS_CONSTANTS.leaflet.TOKEN
        }).addTo(this.gnsmap);

        const marker = L.marker(location).addTo(this.gnsmap);
        marker.bindPopup(`<b>Location</b><br> ${location}`);
    }

    submit(): void {
        if (!this.form.valid) return;
        this.loading = true;
        const payload = { ...this.form.value, created_by: this.browser };
        if (this.formMode === 'edit') {
            this.service.update(payload).subscribe(
                () => this.router.navigate(['/notebooks']),
                () => this.errorMsg = `Could not edit your current note...`
            );
        } else {
            this.service.save(payload).subscribe(
                () => this.router.navigate(['/notebooks']),
                () => this.errorMsg = `Could not save your current note...`
            )
        }
    }

    resetForm(): void {
        this.submitted = false;
        this.loading = false;
        this.errorMsg = '';
        this.errorFields = {};
        this.form.reset();
    }

}
