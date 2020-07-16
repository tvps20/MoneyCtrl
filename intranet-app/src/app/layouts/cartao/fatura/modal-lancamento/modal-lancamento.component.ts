import { Component, OnInit, Input } from '@angular/core';

import { Lancamento } from './../../../../shared/models/lancamento';

@Component({
    selector: 'app-modal-lancamento',
    templateUrl: './modal-lancamento.component.html',
    styleUrls: ['./modal-lancamento.component.css']
})
export class ModalLancamentoComponent implements OnInit {

    @Input() lancamento: Lancamento;

    constructor() { }

    ngOnInit(): void {

    }
}
