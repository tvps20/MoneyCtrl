import { catchError, tap } from 'rxjs/operators';
import { CompradorService } from './../../../user-comprador/services/comprador.service';
import { Observable, empty } from 'rxjs';
import { CotaFatura } from './../../../../shared/models/cota';
import { ActivatedRoute } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';

import { Fatura } from './../../../../shared/models/fatura';
import { Comprador } from './../../../../shared/models/comprador';
import { AlertServiceService } from './../../../../shared/services/alert-service.service';

@Component({
    selector: 'app-fatura-detail',
    templateUrl: './fatura-detail.component.html',
    styleUrls: ['./fatura-detail.component.css'],
})
export class FaturaDetailComponent implements OnInit {

    public disableSelect = new FormControl(false);
    public datemask = [/\d/, /\d/, '/', /\d/, /\d/];
    public valormask = [/\d/, /\d/, ',', /\d/, /\d/, ' R$'];

    public panelOpenState = false;
    public step = 0;
    public fatura: Fatura;
    public cotas: CotaFatura[];
    public compradorSelect$: Observable<Comprador[]>;

    constructor(private route: ActivatedRoute,
        private alertServiceService: AlertServiceService,
        private compradorService: CompradorService) { }

    ngOnInit(): void {
        this.fatura = this.route.snapshot.data['fatura'];
        this.cotas = this.route.snapshot.data['cotas'];
        this.compradorSelect$ = this.listAllCompradores();
        console.log(this.fatura);
     }

    public setStep(index: number) {
        this.step = index;
    }

    public nextStep() {
        this.step++;
    }

    public prevStep() {
        this.step--;
    }

    public listAllCompradores(){
        return this.compradorService.listAll().pipe(
            catchError(error => {
                this.alertServiceService.ShowAlertDanger('Error ao carregar dividas antigas. Tente novamente mais tarde.')
                return empty();
            })
        );
    }
}
