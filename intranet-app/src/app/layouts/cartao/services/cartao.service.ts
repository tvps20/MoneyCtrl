import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormGroup } from '@angular/forms';
import { take, map, tap } from 'rxjs/operators';

import { CrudService } from 'src/app/shared/services/crud-service';
import { Cartao } from 'src/app/shared/models/cartao';
import { Bandeira } from 'src/app/shared/models/bandeira';

@Injectable({
    providedIn: 'root'
})
export class CartaoService extends CrudService<Cartao> {

    private api_url = '/api/cartoes';

    constructor(protected http: HttpClient) {
        super(http, '/api/cartoes');
    }

    public verificaNomeUnico(nome: string){
        return this.http.get(this.api_url + `/valida/valor-unico/${nome}`)
        .pipe(
            map((dados: { alreadySaved: boolean }) => dados.alreadySaved),
            take(1)
        );
    }

    public partoToEntity(form: FormGroup): Cartao {
        let cartao = new Cartao(null, form.get('nome').value);
        let bandeira = new Bandeira(null, form.get('bandeira').value);

        if(!form.get('novaBandeira').value) {
            bandeira = new Bandeira(form.get('bandeiraSelect').value, null);
        }

        cartao.bandeira = bandeira;
        return cartao;
    }
}
