import { VerificaUniqueFieldService } from './../../shared/services/verifica-unique-field.service';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';

import { ValidFormsService } from 'src/app/shared/services/valid-forms.service';
import { FormValidations } from 'src/app/shared/util/form-validations';
import { map } from 'rxjs/operators';

@Component({
    selector: 'app-user-comprador',
    templateUrl: './user-comprador.component.html',
    styleUrls: ['./user-comprador.component.css']
})
export class UserCompradorComponent implements OnInit {

    public formulario: FormGroup;

    constructor(private formBuilder: FormBuilder,
        private validFormsService: ValidFormsService,
        private verificaUniqueFieldService: VerificaUniqueFieldService) { }

    ngOnInit(): void {
        this.formulario = this.createForm();
        this.verificaUniqueFieldService.verificaEmail('email1@email.com').subscribe();
    }

    public onSubmit(): void {
        if (this.formulario.valid) {
            let valueSubmit = this.addRoles(this.formulario);
            console.log(valueSubmit);
            // Transformando o obj em Json
            JSON.stringify(valueSubmit);
            // this.formulario.reset();
        } else {
            this.validFormsService.verificarValidacoesForm(this.formulario);
        }
    }

    public verificarValidField(campo: string): boolean {
        return this.validFormsService.verificarValidField(campo, this.formulario);
    }

    private createForm():FormGroup {
        return this.formBuilder.group({
            nome: [null, [Validators.required, Validators.minLength(6)]],
            sobrenome: [null, [Validators.required, Validators.minLength(6)]],
            perfil: ["COMPRADOR", Validators.required],
            admin: [false],
            username: [null, Validators.minLength(6)],
            email: [null, Validators.email, [this.validarEmail.bind(this)]],
            senha: [null, Validators.required],
            confirmarSenha: [null, [FormValidations.equalsTo('senha')]],
            roles: [[]]
        });
    }

    private addRoles(form: FormGroup): any {
        let valueSubmit = Object.assign({}, form.value);
        if(form.get('admin').value){
            valueSubmit.roles = ["USER", "ADMIN"];
        } else {
            valueSubmit.roles = ["USER"];
        }

        return valueSubmit;
    }


    private validarEmail(formControl: FormControl){
        return this.verificaUniqueFieldService.verificaEmail(formControl.value)
        .pipe(map(emailExist => emailExist ? {emailInvalido: true} : null));
    }
}
