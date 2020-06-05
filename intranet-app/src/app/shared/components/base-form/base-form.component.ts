import { Component, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';

import { ValidFormsService } from '../../services/valid-forms.service';

@Component({
    selector: 'app-base-form',
    template: '<div></div>'
})
export abstract class BaseFormComponent implements OnInit {

    public formulario: FormGroup;

    constructor(public validFormsService: ValidFormsService) { }

    ngOnInit(): void { }

    public abstract submit();

    public abstract createForm();

    public onSubmit() {
        if (this.formulario.valid) {
            this.submit();
        } else {
            this.validFormsService.verificarValidacoesForm(this.formulario);
        }
    }

    public reseteForm() {
        this.formulario.reset();
        this.formulario.get('nome').markAsUntouched();
    }

    public errorMessage(controle: string, label: string) {
        return this.validFormsService.errorMessage(this.formulario.get(controle), label);
    }

    public verificarValidControl(formControl: any): boolean {
        return this.validFormsService.verificarValidField(formControl);
    }
}
