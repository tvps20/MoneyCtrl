import { Component, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';

import { ValidFormsService } from '../../services/valid-forms.service';

@Component({
    selector: 'app-base-form',
    template: '<div></div>'
})
export abstract class BaseFormComponent implements OnInit {

    public formulario: FormGroup;

    constructor(protected validFormsService: ValidFormsService) { }

    ngOnInit(): void { }

    public abstract submit();

    public abstract createForm();

    public abstract reseteForm();

    public abstract createEntity(entity: any, msgSuccess: string, msgError: string);

    public abstract onSelectedEntity(entity: any);

    public abstract onDelete(event: any);

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

    public paginate(array: any[], page_size, page_index) {
        // human-readable page numbers usually start with 1, so we reduce 1 in the first argument
        return array.slice((page_index - 1) * page_size, page_index * page_size);
    }
}
