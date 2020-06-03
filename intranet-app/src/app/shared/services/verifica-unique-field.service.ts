import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map, tap, delay } from 'rxjs/operators';

@Injectable({
    providedIn: 'root'
})
export class VerificaUniqueFieldService {

    constructor(private http: HttpClient) { }

    public verificaEmail(email: string) {
        return this.http.get('assets/dados/verificarUniqueField.json')
            .pipe(delay(2000),
                map((dados: { emails: any[] }) => dados.emails),
                tap(console.log),
                map((dados: { email: string }[]) => dados.filter(v => v.email === email)),
                map((dados: any[]) => dados.length > 0),
            //tap(console.log)
      );
    }
}
