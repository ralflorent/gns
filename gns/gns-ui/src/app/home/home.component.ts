import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'gns-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  readonly pageTitle: string = `Welcome to GNS`;

  constructor() { }

  ngOnInit(): void {}

}
