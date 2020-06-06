import { FormGroup } from '@angular/forms';
import { delay, tap, take } from 'rxjs/operators';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from './../../../environments/environment';

import { Comprador } from './../../shared/models/comprador';
import { User } from 'src/app/shared/models/user';

@Injectable({
    providedIn: 'root'
})
export class UserCompradorService {

    private readonly APIUSER = `${environment.API}/users`;
    private readonly APICOMPRADOR = `${environment.API}/compradores`;

    constructor(private http: HttpClient) { }

    public listAllCompradores() {
        return this.http.get<Comprador[]>(this.APICOMPRADOR)
        .pipe(
            delay(2000),
            tap(console.log)
        );
    }

    public create(user: User) {
        // Take(1) ja se desinscrever do observable
        return this.http.post(this.APICOMPRADOR, user).pipe(take(1));
    }

    public delete(id: number){
        return this.http.delete(`${this.APICOMPRADOR}/${id}`).pipe(take(1));
    }

    public parseToComprador(form: FormGroup): Comprador {
        let comprador: Comprador = new Comprador(form.get('nome').value, form.get('username').value, form.get('senha').value);
        comprador.sobrenome = form.get('sobrenome').value;

        if(form.get('admin').value){
            comprador.roles.push('ADMIN');
        }

        if(form.get('email') !== null && form.get('email').value !== ''){
            comprador.email = form.get('email').value;
            comprador.acesso.push('EMAIL');
        }

        return comprador;
    }
}
