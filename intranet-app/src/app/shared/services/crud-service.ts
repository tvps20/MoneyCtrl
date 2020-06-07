import { HttpClient } from '@angular/common/http';
import { delay, tap, take } from 'rxjs/operators';

export class CrudService<T> {

    constructor(protected http: HttpClient, private  API_URL) { }

    public listAll() {
        // O '| Async' já se desinscrever do obsevable
        // TODO: remover o delay
        return this.http.get<T[]>(this.API_URL)
        .pipe(
            delay(2000),
        //    tap(console.log)
        );
    }

    public findById(id: number) {
         // Take(1) ja se desinscrever do observable
        return this.http.get<T>(`${this.API_URL}/${id}`).pipe(take(1));
    }

    public create(entity: T) {
        return this.http.post(this.API_URL, entity).pipe(take(1));
    }

    public update(entity: T){
        return this.http.put(`${this.API_URL}/${entity['id']}`, entity).pipe(take(1));
    }

    public delete(id: number){
        return this.http.delete(`${this.API_URL}/${id}`).pipe(take(1));
    }
}
