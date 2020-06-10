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
}
