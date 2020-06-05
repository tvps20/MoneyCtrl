import { delay, tap } from 'rxjs/operators';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from './../../../environments/environment';

import { Comprador } from './../../shared/models/comprador';

@Injectable({
    providedIn: 'root'
})
export class UserCompradorService {

    private readonly API = `${environment.API}compradores`;

    constructor(private http: HttpClient) { }

    public listAll() {
        return this.http.get<Comprador[]>(this.API)
        .pipe(
            delay(2000),
            tap(console.log)
        );
    }
}
