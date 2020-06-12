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

    private api_url = '/api/bandeiras';

    constructor(protected http: HttpClient) {
        super(http, '/api/bandeiras');
    }

    public verificaNomeUnico(nome: string){
        return this.http.get(this.api_url + `/valida/valor-unico/${nome}`)
        .pipe(
            map((dados: { alreadySaved: boolean }) => dados.alreadySaved),
            take(1)
        );
    }

    public partoToEntity(form: FormGroup): Bandeira {
        let bandeira = new Bandeira(null, form.get('nome').value);

        return bandeira;
    }
}
