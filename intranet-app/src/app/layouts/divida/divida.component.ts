import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-divida',
  templateUrl: './divida.component.html',
  styleUrls: ['./divida.component.css']
})
export class DividaComponent implements OnInit {

  public valormask = [/\d/, /\d/, ',', /\d/, /\d/, ' R$'];
  
  constructor() { }

  ngOnInit(): void {
  }

}
