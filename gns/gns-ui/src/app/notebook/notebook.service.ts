import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { Observable, of } from 'rxjs';

import { Notebook } from '../shared/models/notebook.model';

@Injectable()
export class NotebookService {

    private baseUrl = './api/notebooks/notebooks.json';

    constructor(private http: HttpClient) { }

    getAll(): Observable<Notebook[] | string> {
        // return this.http.get(this.baseUrl);
        
        return of(NOTEBOOKS).pipe( catchError(this.handleError) );
    }

    search(term: string): Observable<Notebook[] | string> {
        const found: Notebook[] = NOTEBOOKS.filter(n => n.description.includes(term));
        return of(found).pipe( catchError(this.handleError) );
    } 

    private handleError(err: HttpErrorResponse) {
        // in a real world app, we may send the server to some remote logging infrastructure
        // instead of just logging it to the console
        let errorMessage = '';
        if (err.error instanceof Error) {
            // A client-side or network error occurred. Handle it accordingly.
            errorMessage = `An error occurred: ${err.error.message}`;
        } else {
            // The backend returned an unsuccessful response code.
            // The response body may contain clues as to what went wrong,
            errorMessage = `Server returned code: ${err.status}, error message is: ${err.message}`;
        }
        console.error(errorMessage);
        return Observable.throw(errorMessage);
    }
}

const NOTEBOOKS: Notebook[] = [
    {
        id: 1,
        noteId: 'AA00-00001',
        description: 'Sample note 1',
        note: 'Very very very long note',
        latitude: 58.283237,
        longitude: 5.298438,
        date: new Date(2010, 10, 10),
        createdOn: new Date(),
        createdBy: 'Root User'

    },
    {
        id: 2,
        noteId: 'AA00-00002',
        description: 'Sample note 2',
        note: 'Very very very long note',
        latitude: 16.283237,
        longitude: -5.298438,
        date: new Date(2015, 3, 7),
        createdOn: new Date(),
        createdBy: 'Root User'

    },
    {
        id: 3,
        noteId: 'AA00-00003',
        description: 'Sample note 3',
        note: 'Very very very long note',
        latitude: 23.456,
        longitude: -0.78,
        date: new Date(),
        createdOn: new Date(),
        createdBy: 'Root User'

    }
]