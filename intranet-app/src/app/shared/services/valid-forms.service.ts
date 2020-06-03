import { Injectable } from '@angular/core';
import { FormGroup } from '@angular/forms';

@Injectable({
    providedIn: 'root'
})
export class ValidFormsService {

    constructor() { }

    public verificarValidacoesForm(formGroup: FormGroup) {
        // Retorna uma coleção das propriedades.
        Object.keys(formGroup.controls).forEach(campo => {
            const controle = formGroup.get(campo);
            controle.markAsDirty();
            if (controle instanceof FormGroup) {
                this.verificarValidacoesForm(controle);
            }
        })
    }

    public verificarValidField(campo: string, formulario: FormGroup): boolean {
        return !formulario.get(campo).valid &&
            (formulario.get(campo).touched || formulario.get(campo).dirty);
    }
}
