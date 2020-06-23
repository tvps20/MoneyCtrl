import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, RouterStateSnapshot, Resolve } from '@angular/router';
import { Observable, of } from 'rxjs';

import { UserCompradorService } from '../user-comprador/services/user-comprador.service';
import { Comprador } from '../../shared/models/comprador';

@Injectable({
    providedIn: 'root'
})
export class CompradorResolverGuard implements Resolve<Comprador> {

    constructor(private userCompradorService: UserCompradorService) { }

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Comprador> {
        if (route.params && route.params['id']) {
            return this.userCompradorService.findByIdComprador(route.params['id']);
        }

        // caso use a mesma tela de criar e editar
        return of(new Comprador(null, "", "", ""));
    }
}
