import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { catchError, map } from 'rxjs/operators';
import { Observable, of} from 'rxjs';

import { Notebook, GPSLocation, HttpResponse } from '../shared/models';
import { transform } from '../shared/utils/parser';
import { NOTEBOOKS } from '../shared/constants/gns.constants';


@Injectable()
export class NotebookService {

    private baseUrl = 'http://10.10.10.11:8080/api/v1/notes';
    
    constructor(private http: HttpClient) { }

    getAll(): Observable<Notebook[] | string> {
        return of(NOTEBOOKS).pipe( catchError(this.handleError) );
        // return this.http
        //     .get<HttpResponse<Notebook[]>>(`${this.baseUrl}/list`)
        //     .pipe(
        //         map( (response: HttpResponse<Notebook[]>) =>  {
        //             return response
        //                 .data
        //                 .map(e => transform(e) as Notebook);
        //         }), 
        //         catchError(this.handleError) 
        //     );
    }

    getOne(id: number): Observable<Notebook | string> {
        return this.http
            .get<HttpResponse<Notebook>>(`${this.baseUrl}/${id}/details`)
            .pipe(
                map( (response: HttpResponse<Notebook>) => {
                    return transform(response.data) as Notebook;
                }), 
                catchError(this.handleError) 
            );
    }

    search(term: string): Observable<Notebook[] | string> {
        return this.http
            .get<HttpResponse<Notebook[]>>(`${this.baseUrl}/search?q=${term}`)
            .pipe(
                map( (response: HttpResponse<Notebook[]>) =>  {
                    return response
                        .data
                        .map(e => transform(e) as Notebook);
                }), 
                catchError(this.handleError) 
            );
    }

    getLocation(): Observable<GPSLocation | string> {
        const response: GPSLocation = {
            noteId: 'AA00-01000',
            gnsDate: new Date(),
            latitude: 53.104239,
            longitude: 8.851805
        }
        return of(response).pipe( catchError(this.handleError) );
        // return this.http
        //     .get<HttpResponse<GPSLocation>>(`${this.baseUrl}/add`)
        //     .pipe(
        //         map( (response: HttpResponse<GPSLocation>) => {
        //             return transform(response.data) as GPSLocation;
        //         }), 
        //         catchError(this.handleError) 
        //     );
    }

    save(notebook: Notebook): Observable<Notebook | string> {
        return this.http
            .post(`${this.baseUrl}/add`, notebook)
            .pipe(
                map( (response: HttpResponse<Notebook>) => {
                    return transform(response.data) as Notebook;
                }), 
                catchError(this.handleError) 
            );
    }

    update(notebook: Notebook): Observable<Notebook | string> {
        return this.http
            .post(`${this.baseUrl}/update`, notebook)
            .pipe(
                map( (response: HttpResponse<Notebook>) => {
                    return transform(response.data) as Notebook;
                }), 
                catchError(this.handleError) 
            );
    }

    delete(notebook: Notebook): Observable<string> {
        const { id } = notebook;
        return this.http
            .post(`${this.baseUrl}/delete`, { id })
            .pipe(
                map( (response: HttpResponse<null>) => {
                    return response.status;
                }), 
                catchError(this.handleError) 
            );
    }

    private handleError(err: HttpErrorResponse) {

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