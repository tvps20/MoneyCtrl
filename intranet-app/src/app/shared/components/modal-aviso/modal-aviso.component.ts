import { Component, OnInit, EventEmitter, Output, ViewChild, ElementRef } from '@angular/core';

@Component({
    selector: 'app-modal-aviso',
    templateUrl: './modal-aviso.component.html',
    styleUrls: ['./modal-aviso.component.css']
})
export class ModalAvisoComponent implements OnInit {

    @Output() confimaModal = new EventEmitter();
    @ViewChild('modalAviso') modalAviso: any;

    constructor() { }

    ngOnInit(): void {
    }

    public onConfirm() {
        this.confimaModal.emit('sim');
    }
}
