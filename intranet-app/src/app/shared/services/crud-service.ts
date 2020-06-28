import { HttpClient } from '@angular/common/http';
import { take, catchError, tap } from 'rxjs/operators';
import { empty, throwError } from 'rxjs';
import { FormGroup } from '@angular/forms';

import { AlertService } from './alert-service.service';

export abstract class CrudService<T> {

    protected msgErro = 'Error no sevidor ao tentar carregar informações.';

    constructor(protected http: HttpClient, protected API_URL, protected alertService: AlertService) { }

    public listAll() {
        // O '| Async' já se desinscrever do obsevable
        return this.http.get<T[]>(this.API_URL).pipe(
            catchError(error => {
                this.alertService.ShowAlertDanger(this.msgErro)
                return empty();
            })
        );
    }

    public listAllPage(page: number, linesPerPage: number, direction: string, orderBy: string) {
        return this.http.get<T[]>(`${this.API_URL}/page?page=${page}&linesPerPage=${linesPerPage}&direction=${direction}&orderBy=${orderBy}`).pipe(
            catchError(error => {
                this.alertService.ShowAlertDanger(this.msgErro)
                return empty();
            })
        );
    }

    public findById(id: number) {
        // Take(1) ja se desinscrever do observable
        return this.http.get<T>(`${this.API_URL}/${id}`).pipe(take(1)).pipe(
            catchError(error => {
                this.alertService.ShowAlertDanger(this.msgErro)
                return throwError('Error ao tentar buscar entitdade.');
            })
        );
    }

    public create(entity: T) {
        return this.http.post(this.API_URL, entity).pipe(take(1));
    }

    public update(entity: T) {
        return this.http.put(`${this.API_URL}/${entity['id']}`, entity).pipe(take(1));
    }

    public delete(id: number) {
        return this.http.delete(`${this.API_URL}/${id}`).pipe(take(1));
    }

    public abstract parseToEntity(form: FormGroup): T;

    public parseToDateFormat(date: Date) {
        let aux = date.toISOString().slice(0, 10) // yyyy-MM-dd
        aux += " " + date.toLocaleTimeString() // HH:mm:ss

        return aux;
    }
}
