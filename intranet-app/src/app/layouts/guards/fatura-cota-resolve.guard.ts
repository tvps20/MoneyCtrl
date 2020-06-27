import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, RouterStateSnapshot, Resolve } from '@angular/router';
import { Observable, of, empty } from 'rxjs';

import { CotaFatura } from './../../shared/models/cota';
import { FaturaService } from './../cartao/services/fatura.service';

@Injectable({
    providedIn: 'root'
})
export class FaturaCotaResolveGuard implements Resolve<CotaFatura[]> {

    constructor(private faturaService: FaturaService) { }

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<CotaFatura[]> {
        if (route.params && route.params['id']) {
            return this.faturaService.listAllCotas(route.params['id']);
        }

        // caso use a mesma tela de criar e editar
        return empty();
    }
}
