import { catchError } from 'rxjs/operators';
import { Router } from '@angular/router';
import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, RouterStateSnapshot, Resolve } from '@angular/router';
import { Observable, of, empty } from 'rxjs';

import { Fatura } from './../../shared/models/fatura';
import { FaturaService } from './../cartao/services/fatura.service';

@Injectable({
    providedIn: 'root'
})
export class FaturaResolveGuard implements Resolve<Fatura> {

    constructor(private faturaService: FaturaService,
                private router: Router) { }

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Fatura> {
        if (route.params && route.params['id']) {
            return this.faturaService.findById(route.params['id']).pipe(
                catchError(error => {
                    this.router.navigate(['/faturas']);
                    return empty();
                })
            );
        }

        // caso use a mesma tela de criar e editar
        return of(new Fatura(null, '', 0, ''));
    }
}
