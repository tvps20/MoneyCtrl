import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-comprador-detail',
  templateUrl: './comprador-detail.component.html',
  styleUrls: ['./comprador-detail.component.css']
})
export class CompradorDetailComponent implements OnInit {

  public valormask = [/\d/, /\d/, ',', /\d/, /\d/, ' R$'];
  
  constructor() { }

  ngOnInit(): void {
  }

}
