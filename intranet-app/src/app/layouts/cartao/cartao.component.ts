import { Component, OnInit } from '@angular/core';
import {FormControl} from '@angular/forms';

@Component({
  selector: 'app-cartao',
  templateUrl: './cartao.component.html',
  styleUrls: ['./cartao.component.css']
})
export class CartaoComponent implements OnInit {

  public disableSelect: FormControl = new FormControl(true);

  constructor() { }

  ngOnInit(): void {
    
  }

}
