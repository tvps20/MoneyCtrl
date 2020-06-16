import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormGroup } from '@angular/forms';
import { take, map, tap } from 'rxjs/operators';

import { CrudService } from 'src/app/shared/services/crud-service';
import { Bandeira } from 'src/app/shared/models/bandeira';

@Injectable({
    providedIn: 'root'
})
export class BandeiraService extends CrudService<Bandeira> {

    constructor(protected http: HttpClient) {
        super(http, '/api/bandeiras');
    }

    public verificaNomeUnico(nome: string){
        return this.http.get(this.API_URL + `/valida/valor-unico/${nome}`)
        .pipe(
            map((dados: { alreadySaved: boolean }) => dados.alreadySaved),
            take(1)
        );
    }

    public parseToEntity(form: FormGroup): Bandeira {
        let bandeira = new Bandeira(null, form.get('nome').value);

        return bandeira;
    }
}
