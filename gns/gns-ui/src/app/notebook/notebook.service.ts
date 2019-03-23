/**
 * Notebook data service for the notebook API requests
 *
 * Created on March 23, 2019
 * @author Ralph Florent <ralflornt@gmail.com>
 */

import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { catchError, map } from 'rxjs/operators';
import { Observable } from 'rxjs';

import { GNS_CONSTANTS } from '../shared/constants/gns.constants';
import { Notebook, GPSLocation, HttpResponse } from '../shared/models';
import { transform } from '../shared/utils/parser';


@Injectable()
export class NotebookService {

    private baseUrl = GNS_CONSTANTS.api.BASE_URL;
    
    constructor(private http: HttpClient) { }

    getAll(): Observable<Notebook[] | string> {
        return this.http
            .get<HttpResponse<Notebook[]>>(`${this.baseUrl}/list`)
            .pipe(
                map( (response: HttpResponse<Notebook[]>) =>  {
                    return response
                        .data
                        .map(e => transform(e) as Notebook);
                }), 
                catchError(this.handleError) 
            );
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
        return this.http
            .get<HttpResponse<GPSLocation>>(`${this.baseUrl}/add`)
            .pipe(
                map( (response: HttpResponse<GPSLocation>) => {
                    return transform(response.data) as GPSLocation;
                }), 
                catchError(this.handleError) 
            );
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