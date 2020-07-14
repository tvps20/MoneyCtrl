import { Router } from '@angular/router';
import { catchError } from 'rxjs/operators';
import { Injectable } from '@angular/core';
import { Observable, of, empty } from 'rxjs';
import { ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Resolve } from '@angular/router';

import { Cartao } from './../../shared/models/cartao';
import { CartaoService } from './../cartao/services/cartao.service';

@Injectable({
    providedIn: 'root'
})
export class CartaoResolveGuard implements Resolve<Cartao> {

    constructor(private cartaoService: CartaoService,
        private router: Router) { }

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Cartao> {
        if (route.params && route.params['id']) {
            return this.cartaoService.findById(route.params['id']).pipe(
                catchError(error => {
                    this.router.navigate(['/cartoes']);
                    return empty();
                })
            );
        }

        // caso use a mesma tela de criar e editar
        return of(new Cartao(null, ''));
    }
}
