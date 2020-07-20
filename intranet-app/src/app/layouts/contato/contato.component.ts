import { FormBuilder, Validators } from '@angular/forms';
import { Component, OnInit } from '@angular/core';

import { ValidFormsService } from './../../shared/services/valid-forms.service';
import { BaseFormComponent } from 'src/app/shared/components/base-form/base-form.component';

@Component({
    selector: 'app-contato',
    templateUrl: './contato.component.html',
    styleUrls: ['./contato.component.css']
})
export class ContatoComponent extends BaseFormComponent implements OnInit {

    constructor(protected validFormsService: ValidFormsService,
        private formBuilder: FormBuilder) {
        super(validFormsService);
    }

    ngOnInit(): void {
        this.formulario = this.createForm();
    }

    public submit() {
        console.log("Mensagem enviada")
    }

    public createEntity(entity: any, msgSuccess: string, msgError: string) {
        throw new Error("Method not implemented.");
    }

    public createForm() {
        return this.formBuilder.group({
            nome: [null, Validators.required],
            email: [null, Validators.required],
            telefone: [null],
            celular: [null],
            assunto: [null, Validators.required],
            mensagem: [null, Validators.required]
        });
    }

    public defaultValuesForms() {
        throw new Error("Method not implemented.");
    }
}
