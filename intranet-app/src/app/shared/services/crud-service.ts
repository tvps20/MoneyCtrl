import { HttpClient } from '@angular/common/http';
import { delay, tap, take, map } from 'rxjs/operators';
import { FormGroup } from '@angular/forms';

export abstract class CrudService<T> {

    constructor(protected http: HttpClient, protected API_URL) { }

    public listAll() {
        // O '| Async' já se desinscrever do obsevable
        return this.http.get<T[]>(this.API_URL);
    }

    public listAllPage(page: number, linesPerPage: number, direction: string, orderBy: string){
        return this.http.get<T[]>(`${this.API_URL}/page?page=${page}&linesPerPage=${linesPerPage}&direction=${direction}&orderBy=${orderBy}`);
    }

    public findById(id: number) {
         // Take(1) ja se desinscrever do observable
        return this.http.get<T>(`${this.API_URL}/${id}`).pipe(take(1));
    }

    public create(entity: T) {
        // TODO: Remover dalay
        return this.http.post(this.API_URL, entity).pipe(take(1));
    }

    public update(entity: T){
        return this.http.put(`${this.API_URL}/${entity['id']}`, entity).pipe(take(1));
    }

    public delete(id: number){
        return this.http.delete(`${this.API_URL}/${id}`).pipe(take(1));
    }

    public abstract parseToEntity(form: FormGroup): T;
}
