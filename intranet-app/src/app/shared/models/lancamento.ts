import { Cota } from './cota';
import { EntityType, LancamentoType } from './../util/enuns-type.enum';
import { BaseEntity } from './baseEntity';

export class Lancamento extends BaseEntity {

    public descricao: string;
    public observacao: string;
    public dataCompra: string;
    public faturaId: number;
    public tipoLancamento: LancamentoType;
    public qtdParcelas: number;
    public parcelaAtual: number;
    public cotas: Cota[] = [];

    constructor(id: number, descricao: string, dataCompra: string, faturaId: number,
        tipoLancamento: LancamentoType, qtdParcelas: number, parcelaAtual: number) {
        super(id);
        this.descricao = descricao;
        this.dataCompra = dataCompra;
        this.faturaId = faturaId;
        this.tipoLancamento = tipoLancamento;
        this.qtdParcelas = qtdParcelas;
        this.parcelaAtual = parcelaAtual;
        this.tipo = EntityType.LANCAMENTO;
    }
}
