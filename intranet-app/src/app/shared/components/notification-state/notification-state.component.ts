import { Component, OnInit, Input } from '@angular/core';

const CLASSES = [
    { tipo: 'info', name: 'Info' },
    { tipo: 'success', name: 'Sucesso' },
    { tipo: 'warning', name: 'Aviso' },
    { tipo: 'danger', name: 'Error' },
    { tipo: 'primary', name: 'Alerta' }
];

@Component({
    selector: 'app-notification-state',
    templateUrl: './notification-state.component.html',
    styleUrls: ['./notification-state.component.css']
})
export class NotificationStateComponent implements OnInit {

    @Input() tipo = 'info';
    @Input() msg = 'This is a regular notification made with ".alert-info"';
    public classType = 'alert alert-info';
    public nameTipo = 'Info';

    constructor() { }

    ngOnInit(): void {
    }

    public choseTypeClass(){
        let auxClass = 'alert alert-'

        let tipo = CLASSES.filter(x => x.tipo === this.tipo);

        if(tipo.length > 0){
            auxClass = auxClass.concat(tipo[0].tipo);
            this.nameTipo = tipo[0].name;
        }

        return auxClass;
    }
}
