import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NotebookService } from '../notebook.service';
import { Notebook } from 'src/app/shared/models/notebook.model';

@Component({
  selector: 'gns-notebook-detail',
  templateUrl: './notebook-detail.component.html',
  styleUrls: ['./notebook-detail.component.scss']
})
export class NotebookDetailComponent implements OnInit {

  notebook: Notebook = null;
  
  constructor(
    private service: NotebookService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit() {
    const id = +this.route.snapshot.paramMap.get('id');
    this.service.getOne(id)
      .subscribe(
        (data: Notebook) => this.notebook = data,
        (error: string) => console.log(error)
      );
  }

}
