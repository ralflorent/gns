import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { MatPaginator, MatSort, MatTableDataSource } from '@angular/material';
import { ToastrService } from 'ngx-toastr';

import { NotebookService } from '../notebook.service';
import { Notebook } from 'src/app/shared/models/notebook.model';

@Component({
    selector: 'gns-notebooks',
    templateUrl: './notebooks.component.html',
    styleUrls: ['./notebooks.component.scss']
})
export class NotebooksComponent implements OnInit {

    notebooks: Notebook[] = [];
    searchTerm: string = '';
    errorMsg: string = '';
    loading: boolean = false;
    dataSource: any;
    columnNames: string[] = [];
    @ViewChild(MatPaginator) paginator: MatPaginator;
    @ViewChild(MatSort) sort: MatSort;

    constructor(
        private service: NotebookService,
        private router: Router,
        private toastr: ToastrService
    ) { }

    ngOnInit() {
        this.loading = true;
        this.columnNames = [
            'description', 'note', 'latitude', 'longitude', 'gnsDate', 'actions'
        ];
        this.service.getAll()
            .subscribe(
                (data: Notebook[]) => {
                    this.notebooks = data;
                    this.dataSource = new MatTableDataSource<Notebook>(data);
                    this.dataSource.paginator = this.paginator;
                    this.dataSource.sort = this.sort;
                },
                (error: string) => {
                    this.loading = false;
                    this.errorMsg = error;
                },
                () => this.loading = false
            );
    }

    search(term: string): void {
        if (!term) {
            this.errorMsg = `Please enter a valid search term to proceed`;
            this.ngOnInit();
            return;
        }

        this.errorMsg = '';
        this.loading = true;
        this.service.search(term)
            .subscribe(
                (data: Notebook[]) => {
                    this.notebooks = data;
                    this.dataSource = new MatTableDataSource<Notebook>(data);
                    this.dataSource.paginator = this.paginator;
                    if (this.notebooks && this.notebooks.length) return;
                    this.errorMsg = `No results found for '${term}'`;
                },
                (error: string) => {
                    this.loading = false;
                    this.errorMsg = error;
                },
                () => this.loading = false
            );
    }

    applyFilter(term: string): void {
        term = term.trim().toLowerCase(); // clean search term
        this.dataSource.filter = term;
    }

    onDelete(notebook: Notebook): void {
        const confirmed: boolean = confirm(`Are you sure you want to delete ${notebook.noteId}?`);
        if (!confirmed) return;

        this.loading = true;
        // process by deleting the note
        this.service.delete(notebook)
            .subscribe(
                status => {
                    this.toastr.success(`The note from ${notebook.noteId} has been deleted successfully!`);
                    this.ngOnInit();
                },
                (error: string) => {
                    this.loading = false;
                    this.errorMsg = error;
                },
                () => this.loading = false
            )
    }
}
