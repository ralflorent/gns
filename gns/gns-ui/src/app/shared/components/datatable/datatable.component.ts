/**
 * Data table to handle features like paging, sorting, and filtering
 *
 * Created on March 29, 2019
 * @author Ralph Florent <ralflornt@gmail.com>
 */
import { Component, OnInit, ViewChild, Input, Output } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MatPaginator, MatTableDataSource } from '@angular/material';

import { Notebook } from 'src/app/shared/models/notebook.model';

@Component({
  selector: 'gns-datatable',
  templateUrl: './datable.component.html',
  styles: [`
    table {
        width: 100%;
    }
  `]
})
export class GNSDataTableComponent implements OnInit {
    
    @Input('columnNames') columnNames: string[];
    @Input('dataRows') data: Notebook[];

    dataSource = new MatTableDataSource<Notebook>(this.data);

    @ViewChild(MatPaginator) paginator: MatPaginator;

    ngOnInit() {

        this.dataSource.paginator = this.paginator;
    }
}
