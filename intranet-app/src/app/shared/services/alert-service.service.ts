import { AlertTypes, IconsType } from './../util/enuns-type.enum';
import { Injectable } from '@angular/core';
declare var $: any;


@Injectable({
    providedIn: 'root'
})
export class AlertServiceService {

    constructor() { }

    public ShowAlertDanger(msg){
        this.showNotification(msg, IconsType.DANGER, AlertTypes.DANGER);
    }

    public ShowAlertSuccess(msg){
        this.showNotification(msg, IconsType.SUCCESS, AlertTypes.SUCCESS);
    }

    public ShowAlertInfo(msg){
        this.showNotification(msg, IconsType.INFO, AlertTypes.INFO);
    }

    public ShowAlertWarning(msg){
        this.showNotification(msg, IconsType.WARNING, AlertTypes.WARNING);
    }

    public ShowAlertPrimary(msg){
        this.showNotification(msg, IconsType.PRIMARY, AlertTypes.PRIMARY);
    }

    private showNotification(msg: string, icone: string, color: string, from: string = "top", align:string = "right") {
        $.notify({
            icon: icone,
            message: msg
        }, {
            type: color,
            timer: 4000,
            placement: {
                from: from,
                align: align
            },
            template: '<div data-notify="container" class="col-xl-4 col-lg-4 col-11 col-sm-4 col-md-4 alert alert-{0} alert-with-icon" role="alert">' +
                '<button mat-button  type="button" aria-hidden="true" class="close mat-button" data-notify="dismiss">  <i class="material-icons">close</i></button>' +
                '<i class="material-icons" data-notify="icon">'+ icone +'</i> ' +
                '<span data-notify="title">{1}</span> ' +
                '<span data-notify="message">{2}</span>' +
                '</div>' +
                '</div>'
        });
    }
}
