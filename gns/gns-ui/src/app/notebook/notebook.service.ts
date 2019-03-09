import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { Observable, of } from 'rxjs';

import { Notebook } from '../shared/models/notebook.model';
import { NOTEBOOKS } from '../shared/constants/gns.constants';

@Injectable()
export class NotebookService {

    private baseUrl = './api/notebooks/notebooks.json';

    constructor(private http: HttpClient) { }

    getAll(): Observable<Notebook[] | string> {
        // return this.http.get(this.baseUrl);
        return of(NOTEBOOKS).pipe( catchError(this.handleError) );
    }

    getOne(id: number): Observable<Notebook | string> {
        const found: Notebook = NOTEBOOKS.find(n => n.id === id );
        return of(found).pipe( catchError(this.handleError) );
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
        return Observable.throw(errorMessage);
    }
}