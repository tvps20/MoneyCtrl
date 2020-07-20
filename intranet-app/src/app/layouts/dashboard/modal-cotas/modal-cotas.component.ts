import { CotaFatura } from './../../../shared/models/cota';
import { Component, OnInit, Input, OnChanges, SimpleChanges } from '@angular/core';
import { LancamentoType } from 'src/app/shared/util/enuns-type.enum';

@Component({
    selector: 'app-modal-cotas',
    templateUrl: './modal-cotas.component.html',
    styleUrls: ['./modal-cotas.component.css']
})
export class ModalCotasComponent implements OnInit, OnChanges {

    @Input() identificador = "modalCotas";
    @Input() cotaFatura: CotaFatura;

    public totalAssinatura = 0;
    public totalAcabaAgora = 0;

    constructor() { }

    ngOnInit(): void {
    }

    ngOnChanges(changes: SimpleChanges): void {
        let auxTotalAssinatura = 0;
        let auxTotalAcabaAgora = 0;
        this.cotaFatura?.cotas?.map(x => {
            if(x.lancamento.tipoLancamento === LancamentoType.ASSINATURA){
                auxTotalAssinatura += x.valor;
            } else if(x.lancamento.parcelaAtual === x.lancamento.qtdParcelas){
                auxTotalAcabaAgora += x.valor;
            }
        })

        this.totalAssinatura = auxTotalAssinatura;
        this.totalAcabaAgora = auxTotalAcabaAgora;
    }
}
