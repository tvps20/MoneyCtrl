import { Component, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';

import { ValidFormsService } from '../../services/valid-forms.service';

@Component({
    selector: 'app-base-form',
    template: '<div></div>'
})
export abstract class BaseFormComponent implements OnInit {

    public submitte = false;
    public showForm = true;
    public formulario: FormGroup;

    constructor(protected validFormsService: ValidFormsService) { }

    ngOnInit(): void { }

    public abstract submit();

    public abstract createEntity(entity: any, msgSuccess: string, msgError: string);

    public abstract createForm();

    public abstract defaultValuesForms();

    public reseteForm() {
        this.showForm = false;
        setTimeout(() => this.showForm = true);
        this.formulario.reset();
        this.defaultValuesForms();
    }

    public onSubmit() {
        if (this.formulario.valid) {
            this.submit();
        } else {
            this.validFormsService.verificarValidacoesForm(this.formulario);
        }
    }

    public verificarValidControl(control: string): boolean {
        let controlForm = this.formulario.get(control);
        return !controlForm.valid &&
            (controlForm.touched || controlForm.dirty);
    }

    public verificarValidControlSuccess(control: string): boolean {
        let controlForm = this.formulario.get(control);
        return controlForm.valid &&
            (controlForm.value !== null && controlForm.value !== '');
    }

    public errorMessage(control: string, label: string) {
        return this.validFormsService.errorMessage(this.formulario.get(control), label);
    }
}
