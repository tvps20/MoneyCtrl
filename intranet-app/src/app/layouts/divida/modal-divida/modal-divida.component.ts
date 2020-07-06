import { Component, OnInit, Input } from '@angular/core';

import { Divida } from './../../../shared/models/divida';

@Component({
    selector: 'app-modal-divida',
    templateUrl: './modal-divida.component.html',
    styleUrls: ['./modal-divida.component.css']
})
export class ModalDividaComponent implements OnInit {

    @Input() divida: Divida;

    constructor() { }

    ngOnInit(): void {
    }
}
