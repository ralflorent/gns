import { Component, OnInit } from '@angular/core';
import { NotebookService } from '../notebook.service';
import { Notebook } from 'src/app/shared/models/notebook.model';

@Component({
  selector: 'gns-notebooks',
  templateUrl: './notebooks.component.html',
  styleUrls: ['./notebooks.component.scss']
})
export class NotebooksComponent implements OnInit {

  readonly pageTitle: string = `Notebooks`;

  notebooks: Notebook[] = [];
  searchTerm: string = '';
  errorMsg: string = '';

  constructor(private service: NotebookService) { }

  ngOnInit() {
    this.service.getAll()
      .subscribe(
        data => this.notebooks = data as Notebook[],
        error => console.log(error)
      );
  }

  search(term: string): void {
    if (!term) {
      this.errorMsg = `Please enter a valid search term to proceed`;
      this.ngOnInit();
      return;
    }
    
    this.errorMsg = '';
    this.service.search(term)
      .subscribe(
        data => { 
          this.notebooks = data as Notebook[];
          if (this.notebooks && this.notebooks.length) return; 
          this.errorMsg = `No results found for '${term}'`;
        },
        error => console.log(error) 
      );
  }
}
