import { FaturaService } from './../cartao/services/fatura.service';
import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, RouterStateSnapshot, Resolve } from '@angular/router';
import { Observable, of } from 'rxjs';

import { Fatura } from './../../shared/models/fatura';

@Injectable({
    providedIn: 'root'
})
export class FaturaResolveGuard implements Resolve<Fatura> {

    constructor(private faturaService: FaturaService) { }

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Fatura> {
        if (route.params && route.params['id']) {
            return this.faturaService.findById(route.params['id']);
        }

        // caso use a mesma tela de criar e editar
        return of(new Fatura(null, '', 0, ''));
    }
}
