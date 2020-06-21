import { Component, OnInit, EventEmitter, Output, Input } from '@angular/core';

@Component({
    selector: 'app-modal-aviso',
    templateUrl: './modal-aviso.component.html',
    styleUrls: ['./modal-aviso.component.css']
})
export class ModalAvisoComponent implements OnInit {

    @Input() msg = "Deseja realmente excluir esse item?";
    @Input() obs = "Lembre-se que isso não é reversivel.";
    @Input() cancelTxt = "Não";
    @Input() okTxt = "Sim";
    @Input() identificador = "detalhesModalAviso";
    @Output() confimaModal = new EventEmitter();

    constructor() { }

    ngOnInit(): void {
    }

    public onConfirm() {
        this.confimaModal.emit('sim');
    }
}
