import { Injectable } from '@angular/core';
import { FormGroup, FormControl, AbstractControl } from '@angular/forms';
import { FormValidations } from '../util/form-validations';
import { HttpClient } from '@angular/common/http';
import { map, delay, tap } from 'rxjs/operators';

@Injectable({
    providedIn: 'root'
})
export class ValidFormsService {

    constructor(private http: HttpClient) { }

    public verificarValidacoesForm(formGroup: FormGroup) {
        // Retorna uma coleção das propriedades.
        Object.keys(formGroup.controls).forEach(campo => {
            const controle = formGroup.get(campo);
            controle.markAsTouched();
            controle.markAsDirty();
            if (controle instanceof FormGroup) {
                this.verificarValidacoesForm(controle);
            }
        })
    }

    public verificarValidField(formControl: FormControl): boolean {
        return !formControl.valid &&
            (formControl.touched && formControl.dirty);
    }

    public errorMessage(formControl: AbstractControl, label: string) {
        for (let propertyName in formControl.errors) {
            if (formControl.errors.hasOwnProperty(propertyName) &&
                (formControl.touched || formControl.dirty)) {
                return FormValidations.getErrorMsg(label, propertyName, formControl.errors[propertyName]);
            }
        }
        return null;
    }

    public verificaEmail(email: string) {
        return this.http.get('assets/dados/dbUniqueFields.json')
            .pipe
            (
                delay(3000),
                map((dados: { emails: any[] }) => dados.emails),
            //    tap(console.log),
                map((dados: { email: string }[]) => dados.filter(v => v.email === email)),
                map((dados: any[]) => dados.length > 0),
            //    tap(console.log)
            );
    }

    public verificaUsername(username: string) {
        return this.http.get('assets/dados/dbUniqueFields.json')
            .pipe(
                delay(3000),
                map((dados: { usernames: any [] }) => dados.usernames),
                tap(console.log),
                map((dados: { username: string }[]) => dados.filter(v => v.username === username)),
                map((dados: any[]) => dados.length > 0),
            //    tap(console.log)
            );
    }
}
